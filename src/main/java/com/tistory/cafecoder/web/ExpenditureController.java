package com.tistory.cafecoder.web;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.domain.expenditure.Expenditure;
import com.tistory.cafecoder.service.ExpenditureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
public class ExpenditureController {
    private final ExpenditureService expenditureService;

    @GetMapping("/expenditure")
    public String expenditure(Model model, @LoginUser SessionUser user) {
        model.addAttribute(("today"), LocalDate.now().toString());

        if (user != null) {
            model.addAttribute("loginUser", user.getEmail());
        }

        return "expenditure";
    }

    @PostMapping("/expenditure/list")
    public String expenditureList(Model model, @RequestParam("email") String email, @RequestParam("startDate") String start, @RequestParam("endDate") String end, @LoginUser SessionUser user) {
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

        model.addAttribute("loginUser", user.getEmail());
        model.addAttribute("start", startDate.toString());
        model.addAttribute("end", endDate.toString());
        model.addAttribute("expenditureList", this.expenditureService.getMonthList(email, startDate, endDate));

        System.out.println(model.toString());

        return "expenditureList";
    }
}
