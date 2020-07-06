package com.tistory.cafecoder.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/expenditure")
    public String expenditure(Model model) {


        return "expenditure";
    }

    @GetMapping("/income/{year}/{month}")
    public String income(Model model, @PathVariable Long year, @PathVariable Long month) {


        return "income";
    }
}
