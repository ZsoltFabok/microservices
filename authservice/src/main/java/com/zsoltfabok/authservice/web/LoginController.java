package com.zsoltfabok.authservice.web;

import com.zsoltfabok.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(HttpSession session, Model model, @RequestParam String email, @RequestParam String password) {
        if (authService.login(email, password)) {
            session.setAttribute("id", email);
            return "redirect:/";
        } else {
            // FIXME localization
            model.addAttribute("error", "Wrong username or password");
            return "login";
        }
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String register(Model model, @RequestParam String email, @RequestParam String password) {
        if (authService.register(email, password)) {
            return "redirect:/";
        } else {
            // FIXME localization
            // FIXME proper error message that actually helps
            model.addAttribute("error", "registration issue");
            return "login";
        }
    }
}
