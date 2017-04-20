package com.zsoltfabok.authservice.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan("com.zsoltfabok.authservice.domain")
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    public void findsUserByEmail() {
        User user = new User("email", "password");
        entityManager.persist(user);
        assertEquals(user, repository.findByEmail("email"));
        assertNull(repository.findByEmail("email2"));
    }

    @Test
    public void createsUserWhenNotExists() {
        User user = repository.create("email", "password");
        assertNotNull(user);
        assertEquals("password", user.getPassword());
        assertEquals("email", user.getEmail());

        User fromDb = entityManager.find(User.class, "email");

        assertEquals("password", fromDb.getPassword());
        assertEquals("email", fromDb.getEmail());
    }

    @Test
    public void doesNotCreateUserWhenExists() {
        User user = new User("email", "password");
        entityManager.persist(user);
        assertNull(repository.create("email", "password"));
    }
}
