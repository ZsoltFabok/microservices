package com.zsoltfabok.webservice.web;

import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.web.client.RestTemplate;

@RequestMapping("/login")
@Controller
public class LoginController {

    @Autowired
    private ServicesProperties servicesProperties;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(method=RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String loginSuccess(Model model, @RequestParam String email, @RequestParam String password) {

        final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode payload = nodeFactory.objectNode();
        payload.put("email", email);
        payload.put("password", password);

        String authserviceUrl = servicesProperties.getAuthservice().getUrl();
        JsonNode json = restTemplate.postForObject(authserviceUrl, payload, ObjectNode.class);

        if (!"ok".equals(json.get("status").asText())) {
            model.addAttribute("error", true);
            return "login";
        }

        return "redirect:/";
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
