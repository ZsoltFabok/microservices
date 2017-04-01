package com.zsoltfabok.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * sending data to the service with curl:
 *
 * curl -H "Content-Type: application/json" -X POST -d '{"username":"xyz","password":"xyz"}' http://localhost:8081/login
 */
@Controller
public class AuthService {
    @RequestMapping(value="/login", method= RequestMethod.POST)
    public @ResponseBody JsonNode login(@RequestBody JsonNode payload) {
        String username = payload.get("username").asText();
        String password = payload.get("password").asText();

        final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode response = nodeFactory.objectNode();

        if (username.equals(password)) {
            response.put("status", "ok");
        } else {
            response.put("status", "failed");
        }
        return response;
    }
}
