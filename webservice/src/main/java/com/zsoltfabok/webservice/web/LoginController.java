package com.zsoltfabok.webservice.web;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zsoltfabok.webservice.config.ServicesProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private ServicesProperties servicesProperties;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(HttpSession session, Model model, @RequestParam String email, @RequestParam String password) {

        final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode payload = nodeFactory.objectNode();
        payload.put("email", email);
        payload.put("password", password);

        try {
            String authserviceUrl = servicesProperties.getAuthservice().getUrl() + "/login";
            restTemplate.postForObject(authserviceUrl, payload, ObjectNode.class);
            session.setAttribute("id", email);
            return "redirect:/";
        } catch (HttpClientErrorException e) {
            model.addAttribute("error", e.getResponseBodyAsString());
            return "login";
        }
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String register(Model model, @RequestParam String email, @RequestParam String password) {

        final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode payload = nodeFactory.objectNode();
        payload.put("email", email);
        payload.put("password", password);

        try {
            String authserviceUrl = servicesProperties.getAuthservice().getUrl() + "/register";
            restTemplate.postForObject(authserviceUrl, payload, ObjectNode.class);
            return "redirect:/";
        } catch (HttpClientErrorException e) {
            model.addAttribute("error", e.getResponseBodyAsString());
            return "login";
        }
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
