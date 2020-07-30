package com.tistory.cafecoder.web;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.service.StockService;
import com.tistory.cafecoder.web.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class StockController {

    private final StockService stockService;

    @PostMapping("/stock")
    public String stockMain(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("user", user.getEmail());
        }

        return "stock";
    }

    @GetMapping("/stock/list")
    public String stockList(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("user", user.getEmail());
            model.addAttribute("productMap", this.stockService.stockList(user.getEmail()));
        }
        else {
            return "index";
        }

        return "stockList";
    }
}
