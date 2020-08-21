package com.tistory.cafecoder.web;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.domain.income.Income;
import com.tistory.cafecoder.domain.product.Client;
import com.tistory.cafecoder.service.ClientService;
import com.tistory.cafecoder.service.ExpenditureService;
import com.tistory.cafecoder.service.IncomeService;
import com.tistory.cafecoder.web.dto.IncomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final IncomeService incomeService;
    private final ExpenditureService expenditureService;
    private final ClientService clientService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {

        if (user != null) {
            model.addAttribute("loginUser", user.getEmail());
        }

        return "index";
    }


}
