package com.zsoltfabok;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/login")
@Controller
public class LoginController {
    @RequestMapping(method=RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String loginSuccess(Model model) {
        RestTemplate template = new RestTemplate();
        JsonNode json = template.getForObject("http://gturnquist-quoters.cfapps.io/api/random", JsonNode.class);
        model.addAttribute("quote", json.get("value").get("quote"));
        return "login";
    }
}
