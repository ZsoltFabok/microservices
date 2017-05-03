package com.zsoltfabok.authservice.service;

import com.zsoltfabok.authservice.domain.User;
import com.zsoltfabok.authservice.domain.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AuthService.class})
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @MockBean
    private UserRepository repository;

    @Mock
    private User user;

    @Test
    public void returnsOkWhenTheUsersPasswordMatches() throws Exception {
        when(repository.findByEmail("email")).thenReturn(user);
        when(user.getPassword()).thenReturn("password");

        assertTrue(authService.login("email", "password"));
    }

    @Test
    public void returnsFailedWhenThePasswordDoesNotMatches() throws Exception {
        when(repository.findByEmail("email")).thenReturn(user);
        when(user.getPassword()).thenReturn("another");

        assertFalse(authService.login("email", "password"));
    }

    @Test
    public void returnsOkWhenTheUserWasSuccessfullyCreated() throws Exception {
        User newUser = new User("email", "password");
        when(repository.create("email", "password")).thenReturn(newUser);

        assertTrue(authService.register("email", "password"));
    }

    @Test
    public void returnsFailedWhenTheUserAlreadyExists() throws Exception {
        when(repository.create("email", "zsolt")).thenReturn(null);

        assertFalse(authService.register("email", "password"));
    }
}
