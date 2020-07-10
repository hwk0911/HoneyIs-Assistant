package com.tistory.cafecoder.web;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.domain.income.Income;
import com.tistory.cafecoder.service.IncomeService;
import com.tistory.cafecoder.web.dto.IncomeDto;
import com.tistory.cafecoder.web.dto.TermIncomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final IncomeService incomeService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {

        if(user != null) {
            model.addAttribute("user", user.getEmail());
        }

        return "index";
    }

    @GetMapping("/expenditure")
    public String expenditure(Model model) {


        return "expenditure";
    }

    @GetMapping("/income")
    public String income(Model model, @LoginUser SessionUser user) {
        model.addAttribute(("today"), LocalDate.now().toString());

        if(user != null) {
            model.addAttribute("user", user.getEmail());
        }

        return "income";
    }

    @PostMapping("/incomelist")
    public String getMonthList(Model model, @RequestParam("email") String email, @RequestParam("startDate") String start, @RequestParam("endDate") String end) {
        LocalDate startDate;
        LocalDate endDate;

        try {
            endDate = LocalDate.parse(end);
        }
        catch (Exception e) {
            endDate = LocalDate.now();
        }
        try {
            startDate = LocalDate.parse(start);
        }
        catch (Exception e) {
            startDate = LocalDate.of(endDate.getYear(), endDate.getMonthValue(), 1);
        }

        model.addAttribute("start", startDate.toString());
        model.addAttribute("end", endDate.toString());
        model.addAttribute("incomeList", this.incomeService.getMonthList(email, startDate, endDate));

        System.out.println(model.toString());

        return "incomeList";
    }
}
