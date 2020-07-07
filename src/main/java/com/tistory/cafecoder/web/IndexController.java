package com.tistory.cafecoder.web;

import com.tistory.cafecoder.domain.income.Income;
import com.tistory.cafecoder.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final IncomeService incomeService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/expenditure")
    public String expenditure(Model model) {


        return "expenditure";
    }

    @GetMapping("/income")
    public String income(Model model) {
        List<Income> incomeList = this.incomeService.getMonthList("null");

        model.addAttribute("incomeList", incomeList);
        model.addAttribute("year", incomeList.get(0).getDate().getYear());
        model.addAttribute("month", incomeList.get(0).getDate().getMonthValue());

        for(Income income : incomeList) {
            System.out.println(income.toString());
        }

        return "income";
    }

    @PostMapping("/api/v1/getMonthList")
    public String getMonthList(Model model, @RequestParam("where") String where, @RequestParam("year") int year, @RequestParam("month") int month) {
        List<Income> incomeList;

        System.out.println(year + " " + month);

        if(where.equals("prev")) {
            year -= (month - 1) < 1 ? 1 : 0;
            month = (month - 1) < 1 ? 12 : month - 1;
        }
        else {
            year += (month + 1) > 12 ? 1 : 0;
            month = (month + 1) > 12 ? 1 : month + 1;
        }
        incomeList = this.incomeService.getMonthList(year, month);

        model.addAttribute("incomeList", incomeList);
        model.addAttribute("year", year);
        model.addAttribute("month", month);

        return "income";
    }
}
