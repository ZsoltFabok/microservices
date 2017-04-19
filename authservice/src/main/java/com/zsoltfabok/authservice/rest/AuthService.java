package com.zsoltfabok.authservice.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zsoltfabok.authservice.domain.User;
import com.zsoltfabok.authservice.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * sending data to the service with curl:
 *
 * curl -H "Content-Type: application/json" -X POST -d '{"email":"xyz","password":"xyz"}' http://localhost:8081/login
 */
@Controller
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public @ResponseBody JsonNode login(@RequestBody JsonNode payload) {
        String email = payload.get("email").asText();
        String password = payload.get("password").asText();

        final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode response = nodeFactory.objectNode();

        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            response.put("status", "ok");
        } else {
            // FIXME in this case return with a different error code not 200
            response.put("status", "failed");
        }
        return response;
    }
}