package com.zsoltfabok.webservice.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void showsTheMainPageWhenTheUserIsNotLoggedIn() throws Exception {
        mvc.perform(get("/"))
            .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void showsTheMainPageWhenTheUserIsLoggedIn() throws Exception {
        Map<String, Object> session = new HashMap<>();
        session.put("id", "zsolt");

        mvc.perform(get("/")
                .sessionAttrs(session))
                .andExpect(status().isOk())
                .andExpect(xpath("/html/head/title").string(containsString("Microservices")));
    }
}

