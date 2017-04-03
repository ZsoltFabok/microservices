package com.zsoltfabok;

import com.zsoltfabok.service.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthService.class)
public class AuthServiceTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void returnsOkWhenTheUserNameEqualsThePassword() throws Exception {
        String request = "{\"username\":\"zsolt\", \"password\":\"zsolt\"}";
        mvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is("ok")));
    }

    @Test
    public void returnsFailedWhenTheUserNameDoesNotMatchThePassword() throws Exception {
        String request = "{\"username\":\"zsolt\", \"password\":\"zs\"}";
        mvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is("failed")));
    }
}
