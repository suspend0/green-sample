package com.sample.common.store;

import com.sample.common.model.Account;
import com.sample.common.model.Customer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * @author Oliver Gierke
 */
@ContextConfiguration("classpath:application-context-after.xml")
public class AccountRepositoryIntegrationTest extends AbstractShowcaseTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void savesAccount() {

        Account account = accountRepository.save(new Account());
        assertThat(account.getId(), is(notNullValue()));
    }

    @Test
    public void findsCustomersAccounts() {

        Customer customer = customerRepository.findOne(1L);
        List<Account> accounts = accountRepository.findByCustomer(customer);

        assertFalse(accounts.isEmpty());
        assertThat(accounts.get(0).getCustomer(), is(customer));
    }
}
