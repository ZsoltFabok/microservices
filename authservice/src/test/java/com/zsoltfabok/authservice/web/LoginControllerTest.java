package com.zsoltfabok.authservice.web;


import com.zsoltfabok.authservice.service.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthService authService;

    @Test
    public void showsTheLoginPage() throws Exception {
        mvc.perform(get("/login"))
            .andExpect(status().isOk())
            .andExpect(xpath("/html/body//div[@class='panel-body']//form[@id='login-form']").exists());
    }

    @Test
    public void loginSuccessWhenUserNameAndPasswordMatches() throws Exception {
        when(authService.login("email", "password")).thenReturn(true);

        mvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("email", "email")
            .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void loginFailsWithWrongPasswordAndGoesBackToLoginPage() throws Exception {
        when(authService.login("email", "password")).thenReturn(false);

        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "email")
                .param("password", "password"))
                    .andExpect(status().isOk())
                    .andExpect(xpath("/html/body//div[@class='panel-body']//form[@id='login-form']/div[@class='alert alert-error']/text()")
                            .string(containsString("Invalid username and password")))
                    .andExpect(xpath("/html/body//div[@class='panel-body']//form[@id='login-form']").exists());
    }
}
