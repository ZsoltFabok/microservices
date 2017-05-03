package com.zsoltfabok.authservice.web;


import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zsoltfabok.authservice.service.AuthService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

// FIXME
// @RunWith(SpringRunner.class)
// @WebMvcTest(LoginController.class)
public class LoginControllerTest {
    @Autowired
    private MockMvc mvc;

//    private JsonNodeFactory nodeFactory;
//    private ObjectNode formParam;
//
//
//    @Before
//    public void setup() {
//        nodeFactory = JsonNodeFactory.instance;
//
//        formParam = nodeFactory.objectNode();
//        formParam.put("email", "zsolt");
//        formParam.put("password", "zsolt");
//    }

    @Ignore
    public void showsTheLoginPage() throws Exception {
        mvc.perform(get("/login"))
            .andExpect(status().isOk())
            .andExpect(xpath("/html/body//div[@class='panel-body']//form[@id='login-form']").exists());
    }

//    @Test
//    public void loginSuccessWhenUserNameEqualsPassword() throws Exception {
//
//        ObjectNode response = nodeFactory.objectNode();
//        response.put("status", "ok");
//        when(restTemplate.postForObject(authserviceUrl, formParam, ObjectNode.class)).thenReturn(response);
//
//        mvc.perform(post("/login")
//            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//            .param("email", "zsolt")
//            .param("password", "zsolt"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/"));
//    }
//
//    @Test
//    public void loginFailsWhenUserNameDoesNotEqualToPasswordAndGoesBackToLoginPage() throws Exception {
//
//        ObjectNode response = nodeFactory.objectNode();
//        response.put("status", "failed");
//        when(restTemplate.postForObject(authserviceUrl + "/login", formParam, ObjectNode.class))
//                .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN, response.toString()));
//
//        mvc.perform(post("/login")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("email", "zsolt")
//                .param("password", "zsolt"))
//                    .andExpect(status().isOk())
//                    .andExpect(xpath("/html/body//div[@class='panel-body']//form[@id='login-form']/div[@class='alert alert-error']/text()")
//                            .string(containsString("Invalid username and password")))
//                    .andExpect(xpath("/html/body//div[@class='panel-body']//form[@id='login-form']").exists());
//    }
}
