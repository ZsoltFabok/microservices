package com.zsoltfabok.authservice.domain;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class UserRepository {
    public User findByEmail(String email) {
        return entityManager.find(User.class, email);
    }

    @PersistenceContext
    private EntityManager entityManager;
}
