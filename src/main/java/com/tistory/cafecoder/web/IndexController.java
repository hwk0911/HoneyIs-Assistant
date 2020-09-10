package com.tistory.cafecoder.web;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.service.StockService;

import lombok.RequiredArgsConstructor;

import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private final StockService stockService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("loginUser", user.getEmail());

            HashSet<String> undefinedStockSet = this.stockService.undefinedStock(user.getEmail());

            if (undefinedStockSet != null) {
                model.addAttribute("undefinedCount", !undefinedStockSet.isEmpty());
                model.addAttribute("productMap", undefinedStockSet);
            }

            log.info("{}", user.getName());
        }

        //todo: 공지사항 모델링


        return "index";
    }
}
