package com.zsoltfabok.authservice.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zsoltfabok.authservice.domain.User;
import com.zsoltfabok.authservice.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


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
    public ResponseEntity<ObjectNode> login(@RequestBody JsonNode payload) {
        String email = payload.get("email").asText();
        String password = payload.get("password").asText();

        final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode body = nodeFactory.objectNode();

        User user = userRepository.findByEmail(email);

        HttpStatus status;
        if (user != null && user.getPassword().equals(password)) {
            status = HttpStatus.OK;
            body.put("status", "ok");
        } else {
            status = HttpStatus.FORBIDDEN;
            body.put("status", "failed");
        }
        return new ResponseEntity<>(body, status);
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public ResponseEntity<ObjectNode> register(@RequestBody JsonNode payload) {
        String email = payload.get("email").asText();
        String password = payload.get("password").asText();

        final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
        ObjectNode body = nodeFactory.objectNode();

        User user = userRepository.create(email, password);

        HttpStatus status;
        if (user != null) {
            status = HttpStatus.OK;
            body.put("status", "ok");
        } else {
            status = HttpStatus.CONFLICT;
            body.put("status", "failed");
        }
        return new ResponseEntity<>(body, status);
    }
}