Simple play project exploring spring, hibernate circa 2013.  This is based on code from https://github.com/SpringSource/spring-data-jpa-examples and well as other and sundry blog posts and what not.

Running:

  * mvn jetty:run (or equiv from IDE of choice)
  * localhost port 8080

Structure: 

 * package controller - the 'C' in MVC
 * package model - the 'M' in MVC
 * package store - data storage API

Some notes:

 1. In addition to basic Spring, this is also using "Spring Data" which has a bunch of convenience things around CRUD operations.  In particular note how there is no implementation of the repositories in the 'store' package.  They are magically created.  See [this tutorial](http://blog.springsource.org/2011/02/10/getting-started-with-spring-data-jpa/) for details.
 
 1. There's a controller class return JSON. You can hit it at /green-sample/rest/kfc/brands/foo
 
 1. Note the dependency injection uses the annotation-style (yay!) and classpath scanning (boo!).  Having everything in front of you with annotations is awesome, but classpath scanning gives me the creeps, but maybe beets XML.  I am not sure about this balance, what the relationship between these is or what.
 
 1. I don't have a front-end view model at all.