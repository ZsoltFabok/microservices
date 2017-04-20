package com.zsoltfabok.authservice.domain;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepository {
    public User findByEmail(String email) {
        return entityManager.find(User.class, email);
    }

    /** returns null if the user already exists */
    @Transactional
    public User create(String email, String password) {
        if (entityManager.find(User.class, email) == null) {
            User newUser = new User(email, password);
            entityManager.persist(new User(email, password));
            return newUser;
        } else {
            return null;
        }
    }

    @PersistenceContext
    private EntityManager entityManager;
}
