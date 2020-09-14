package com.tistory.cafecoder.web;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.service.ClientService;
import com.tistory.cafecoder.service.StockService;

import com.tistory.cafecoder.web.dto.ProductDto;
import lombok.RequiredArgsConstructor;

import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private final StockService stockService;
    private final ClientService clientService;

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

    @GetMapping("/search/total")
    public String totalSearch(@RequestParam("searchWord") String searchWord, @LoginUser SessionUser user, Model model) {
        if (user != null) {
            model.addAttribute("loginUser", user.getEmail());

            model.addAttribute("clientList", this.clientService.search(searchWord, user.getEmail()));
            model.addAttribute("clientResult", this.stockService.stockClientSearch(searchWord, user.getEmail()));
            model.addAttribute("stockResult", this.stockService.stockSearch(searchWord, user.getEmail()));
        } else {
            return "index";
        }

        return "search";
    }

}
