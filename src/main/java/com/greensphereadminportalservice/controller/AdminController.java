package com.greensphereadminportalservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/home")
    public String home() {
        return "pages/home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/adminusers")
    public String adminusers() {
        return "pages/admin-users";
    }

    @GetMapping("/editadminusers")
    public String editadminusers() {
        return "pages/editadmins";
    }

    @GetMapping("/setroles")
    public String setroles() {
        return "pages/setroles";
    }
}
