package com.zsoltfabok.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/login")
@Controller
public class LoginController {
    @RequestMapping(method=RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String loginSuccess(Model model, @RequestParam String username, @RequestParam String password) {

        final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode payload = nodeFactory.objectNode();
        payload.put("username", username);
        payload.put("password", password);

        RestTemplate template = new RestTemplate();
        // FIXME this url should come from configuration
        JsonNode json = template.postForObject("http://localhost:8081/login", payload, ObjectNode.class);

        if (!"ok".equals(json.get("status").asText())) {
            model.addAttribute("error", true);
            return "login";
        }

        return "redirect:/";
    }
}
