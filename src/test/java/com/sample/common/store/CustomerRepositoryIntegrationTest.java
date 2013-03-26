package com.sample.common.store;

import com.sample.common.model.Customer;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static com.sample.common.store.CustomerSpecifications.accountExpiresBefore;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author Oliver Gierke
 */
@ContextConfiguration("classpath:application-context-after.xml")
public class CustomerRepositoryIntegrationTest extends AbstractShowcaseTest {

    @Autowired
    CustomerRepository repository;

    @Test
    public void findsAllCustomers() throws Exception {

        Iterable<Customer> result = repository.findAll();

        assertThat(result, is(notNullValue()));
        assertTrue(result.iterator().hasNext());
    }

    @Test
    public void findsFirstPageOfMatthews() throws Exception {

        Page<Customer> customers = repository.findByLastname("Matthews", new PageRequest(0, 2));

        assertThat(customers.getContent().size(), is(2));
        assertFalse(customers.hasPreviousPage());
    }

    @Test
    public void findsCustomerById() throws Exception {

        Customer customer = repository.findOne(2L);

        assertThat(customer.getFirstname(), is("Carter"));
        assertThat(customer.getLastname(), is("Beauford"));
    }

    @Test
    public void findsCustomersBySpecification() throws Exception {

        Customer dave = repository.findOne(1L);

        LocalDate expiryLimit = new LocalDate(2011, 3, 1);
        List<Customer> result = repository.findAll(where(accountExpiresBefore(expiryLimit)));

        assertThat(result.size(), is(1));
        assertThat(result, hasItems(dave));
    }
}
