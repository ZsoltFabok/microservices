package com.zsoltfabok.webservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(HttpSession session) {
        if (session.getAttribute("id") == null) {
            return "redirect:/login";
        }
        return "index";
    }
}