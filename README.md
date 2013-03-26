Simple play project exploring spring, hibernate circa 2013.  This is based on code from https://github.com/SpringSource/spring-data-jpa-examples and well as other and sundry blog posts and what not.

Running:

  * mvn jetty:run (or equiv from IDE of choice)
  * localhost port 8080

Structure: 

 * package controller - the 'C' in MVC
 * package model - the 'M' in MVC
 * package store - data storage API

#### Basic notes

 1. In addition to basic Spring, this is also using "Spring Data" which has a bunch of convenience things around CRUD operations.  In particular note how there is no implementation of the repositories in the 'store' package.  They are magically created.  See [this tutorial](http://blog.springsource.org/2011/02/10/getting-started-with-spring-data-jpa/) for details.
 
 1. There's a controller class return JSON. You can hit it at /green-sample/rest/kfc/brands/foo
 
 1. Note the dependency injection uses the annotation-style (yay!) and classpath scanning (boo!).  Having everything in front of you with annotations is awesome, but classpath scanning gives me the creeps, but maybe beets XML.  I am not sure about this balance, what the relationship between these is or what.
 
 1. I don't have a front-end view model at all.
 
 1. There are a few query mechanisms, the newest of which is "QueryDSL" -- this [blog post](http://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-five-querydsl/) has a quick tutorial.

#### Commentary

 1. The default "pageable" implementation looks like it runs a count query to figure out the total number of items before running the query itself.  Not only is this a bit racy, it also doesn't scale very well against large data sets or complex queries.  So, it's something to be used judiciously, and NOT as a way to break up work into batches.  Looks like the find():Iterable methods are a better direction, but ...
 
 1. Although we're using Hibernate underneath, it's spring-data on top, with JPA in the middle.  By default both Hibernate and JPA put the results in a List in memory.  Any query that returns, say 50K rows will be hurtful.  Ouch.
    * JPA doesn't have any support for large-result-set queries. So to do our "real work" we'll need to abandon the niceties of spring-data and JPA.
    * Hibernate does have some support for large-result queries, through its [StatelessSession interface and ScrollableResults object](http://docs.jboss.org/hibernate/core/3.3/reference/en/html/batch.html#batch-statelesssession).  This resembles JDBC more than regular hibernate, but it does share some things.
    * Hibernate also has support for large results sets through the use of [batch loading](http://docs.jboss.org/hibernate/orm/3.3/reference/en-US/html/performance.html#performance-fetching-batch) on top "proxy fetching".  Bascially we do the query for X identifiers, creating proxy objects for each, and load N at a time from the DB.  And then clear the session too, I guess.  So there's a bit of tediousness here.
    * I'd like to get a better understanding of how much of the app is "domain model persistence" (Hibernate's raison d'etre) and how much is "bulk processing" -- decidedly not Hibernate's forte, before making a decision here.


