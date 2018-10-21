package com.example.hibernateimmutable;

import com.example.hibernateimmutable.entity.Customer;
import com.example.hibernateimmutable.entity.Issue;
import com.example.hibernateimmutable.repository.CustomerRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by mtumilowicz on 2018-10-21.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ImmutableTest {

    @Autowired
    CustomerRepository repository;
    
    @After
    public void deleteAll() {
        repository.deleteAll();
    }

    @Test
    public void modifyImmutableEntity_thenSave() {
//        given
        Issue issue = Issue.builder()
                .id(1)
                .description("issue1")
                .build();

        Customer customer = Customer.builder()
                .id(1)
                .name("customer1")
                .issues(Collections.singletonList(issue))
                .build();

        repository.save(customer);
        
//        when
        Customer modified = Customer.builder()
                .id(1)
                .name("customer2")
                .build();

        repository.save(modified);
        
//        then
        Customer customer1 = repository.findById(1).orElseThrow(EntityNotFoundException::new);
        assertThat(customer1.getName(), is("customer1"));
    }
    
    @Test(expected = JpaSystemException.class)
    public void modifyImmutableCollection_thenSave() {
//        given
        Issue issue1 = Issue.builder()
                .id(1)
                .description("issue1")
                .build();

        Issue issue2 = Issue.builder()
                .id(2)
                .description("issue2")
                .build();

        Customer customer = Customer.builder()
                .id(1)
                .name("customer1")
                .issues(Collections.singletonList(issue1))
                .build();

        repository.save(customer);

//        when
        Customer modified = Customer.builder()
                .id(1)
                .name("customer2")
                .issues(Arrays.asList(issue1, issue2))
                .build();

        repository.save(modified);
    }
}
