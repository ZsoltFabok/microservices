package com.zsoltfabok.authservice.rest;

import com.zsoltfabok.authservice.domain.User;
import com.zsoltfabok.authservice.domain.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthService.class)
public class AuthServiceTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository repository;

    @Mock
    private User user;

    @Test
    public void returnsOkWhenTheUsersPasswordMatches() throws Exception {
        when(repository.findByEmail("email")).thenReturn(user);
        when(user.getPassword()).thenReturn("zsolt");

        String request = "{\"email\":\"email\", \"password\":\"zsolt\"}";
        mvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is("ok")));
    }

    @Test
    public void returnsFailedWhenThePasswordDoesNotMatches() throws Exception {
        when(repository.findByEmail("email")).thenReturn(user);
        when(user.getPassword()).thenReturn("another");

        String request = "{\"email\":\"email\", \"password\":\"zsolt\"}";
        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is("failed")));
    }

    @Test
    public void returnsFailedWhenTheEmailDoesNotMatchThePassword() throws Exception {
        when(repository.findByEmail("email")).thenReturn(null);
        String request = "{\"email\":\"email\", \"password\":\"zs\"}";
        mvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is("failed")));
    }
}
