package com.tistory.cafecoder.web;

import com.tistory.cafecoder.domain.income.Income;
import com.tistory.cafecoder.service.IncomeService;
import com.tistory.cafecoder.web.dto.IncomeDto;
import com.tistory.cafecoder.web.dto.TermIncomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

        for (Income income : incomeList) {
            System.out.println(income.toString());
        }

        return "income";
    }

    @PostMapping("/incomelist")
    public String getMonthList(Model model, @RequestParam("startDate") String start, @RequestParam("endDate") String end) {
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
        model.addAttribute("incomeList", this.incomeService.getMonthList(startDate, endDate));

        System.out.println(model.toString());

        return "incomeList";
    }
}
