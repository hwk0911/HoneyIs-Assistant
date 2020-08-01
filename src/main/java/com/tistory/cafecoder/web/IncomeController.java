package com.tistory.cafecoder.web;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping("/income")
    public String income(Model model, @LoginUser SessionUser user) {
        model.addAttribute(("today"), LocalDate.now().toString());

        if (user != null) {
            model.addAttribute("user", user.getEmail());
        }

        return "income";
    }

    @PostMapping("/income/list")
    public String incomeList(Model model, @RequestParam("email") String email, @RequestParam("startDate") String start, @RequestParam("endDate") String end, @LoginUser SessionUser user) {
        LocalDate startDate;
        LocalDate endDate;

        try {
            endDate = LocalDate.parse(end);
        } catch (Exception e) {
            endDate = LocalDate.now();
        }
        try {
            startDate = LocalDate.parse(start);
        } catch (Exception e) {
            startDate = LocalDate.of(endDate.getYear(), endDate.getMonthValue(), 1);
        }
        if(startDate.isAfter(endDate)) {
            LocalDate temp = startDate;
            startDate = endDate;
            endDate = temp;
        }

        model.addAttribute("user", user.getEmail());
        model.addAttribute("start", startDate.toString());
        model.addAttribute("end", endDate.toString());
        model.addAttribute("incomeList", this.incomeService.getMonthList(email, startDate, endDate));

        System.out.println(model.toString());

        return "incomeList";
    }
}
