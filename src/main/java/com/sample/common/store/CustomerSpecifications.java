package com.sample.common.store;

import com.sample.common.model.Account;
import com.sample.common.model.Customer;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Date;

/**
 * Collection of {@link Specification} implementations.
 *
 * @author Oliver Gierke
 */
public class CustomerSpecifications {

    /**
     * All customers with an {@link com.sample.common.model.Account} expiring before the given date.
     */
    public static Specification<Customer> accountExpiresBefore(final LocalDate date) {

        return new Specification<Customer>() {

            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                Root<Account> accounts = query.from(Account.class);
                Path<Date> expiryDate = accounts.get("expiryDate");
                Predicate customerIsAccountOwner = cb.equal(accounts.<Customer>get("customer"), root);
                Predicate accountExpiryDateBefore = cb.lessThan(expiryDate, date.toDateMidnight().toDate());

                return cb.and(customerIsAccountOwner, accountExpiryDateBefore);
            }
        };
    }
}
