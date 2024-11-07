package com.ratings.rating.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/main")
    public String index() {
        return "main";
    }

    @PostMapping("/fetchRating")
    public String fetchRating(@RequestParam String platform) {
        if ("codeforces".equalsIgnoreCase(platform)) {
            return "redirect:/codeforces";
        } else if ("codechef".equalsIgnoreCase(platform)) {
            return "redirect:/codechef";
        } else {
            // Handle unknown platform
            return "redirect:/";
        }
    }
}
