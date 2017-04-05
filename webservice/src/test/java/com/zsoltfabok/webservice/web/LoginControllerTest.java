package com.zsoltfabok.webservice.web;


import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zsoltfabok.webservice.config.ServicesProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;

    @MockBean(answer=Answers.RETURNS_DEEP_STUBS)
    private ServicesProperties servicesProperties;

    private JsonNodeFactory nodeFactory;
    private ObjectNode formParam;
    private String authserviceUrl;

    @Before
    public void setup() {
        nodeFactory = JsonNodeFactory.instance;

        formParam = nodeFactory.objectNode();
        formParam.put("username", "zsolt");
        formParam.put("password", "zsolt");

        authserviceUrl = "url";
        when(servicesProperties.getAuthservice().getUrl()).thenReturn(authserviceUrl);
    }

    @Test
    public void showsTheLoginPage() throws Exception {
        mvc.perform(get("/login"))
            .andExpect(status().isOk())
            .andExpect(xpath("/html/body/div/form[@name='login-form']").exists());
    }

    @Test
    public void loginSuccessWhenUserNameEqualsPassword() throws Exception {

        ObjectNode response = nodeFactory.objectNode();
        response.put("status", "ok");
        when(restTemplate.postForObject(authserviceUrl, formParam, ObjectNode.class)).thenReturn(response);

        mvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("username", "zsolt")
            .param("password", "zsolt"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void loginFailsWhenUserNameDoesNotEqualToPasswordAndGoesBackToLoginPage() throws Exception {

        ObjectNode response = nodeFactory.objectNode();
        response.put("status", "failed");
        when(restTemplate.postForObject(authserviceUrl, formParam, ObjectNode.class)).thenReturn(response);

        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "zsolt")
                .param("password", "zsolt"))
                    .andExpect(status().isOk())
                    .andExpect(xpath("/html/body/div/form/fieldset/div[@class='alert alert-error']/text()")
                            .string(containsString("Invalid username and password")))
                    .andExpect(xpath("/html/body/div/form[@name='login-form']").exists());
    }
}
