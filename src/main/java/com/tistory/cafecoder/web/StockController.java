package com.tistory.cafecoder.web;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.service.ClientService;
import com.tistory.cafecoder.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class StockController {

    private final StockService stockService;
    private final ClientService clientService;

    @PostMapping("/stock")
    public String stockMain(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("loginUser", user.getEmail());
        }

        return "stock";
    }

    @GetMapping("/stock")
    public String stock(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("loginUser", user.getEmail());
            model.addAttribute("clientList", this.clientService.searchAll(user.getEmail()));
        }

        return "stock";
    }

    @GetMapping("/stock/list")
    public String stockList(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("loginUser", user.getEmail());
            model.addAttribute("productMap", this.stockService.stockList(user.getEmail()));
        }
        else {
            return "index";
        }

        return "stockList";
    }


    @GetMapping("/stock/client/search")
    public String stockClientSearch(@RequestParam("searchword") String searchWord, @LoginUser SessionUser user, Model model) {
        if (user == null) {
            return "redirect:/oauth2/authorization/google";
        }
        model.addAttribute("loginUser", user.getEmail());

        if (searchWord.equals("")) {
            model.addAttribute("clientList", this.clientService.searchAll(user.getEmail()));
        }
        else {
            model.addAttribute("clientList", this.clientService.search(searchWord, user.getEmail()));
        }

        return "stock";
    }
}
