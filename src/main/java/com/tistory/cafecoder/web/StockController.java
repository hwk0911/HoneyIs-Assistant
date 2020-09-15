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

    @GetMapping("/stock/search")
    public String searchStock (@RequestParam("searchWord") String searchWord, @LoginUser SessionUser user, Model model) {
        if(user != null) {
            model.addAttribute("loginUser", user.getEmail());

            model.addAttribute("clientResult", this.stockService.stockClientSearch(searchWord, user.getEmail()));
            model.addAttribute("stockResult", this.stockService.stockSearch(searchWord, user.getEmail()));
        }
        else {
            return "index";
        }

        return "search";
    }

    @GetMapping("/stock/radio")
    public String searchClientPopup (Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("loginUser", user.getEmail());
            model.addAttribute("productMap", this.stockService.removeUndefineStock(this.stockService.stockList(user.getEmail()), user.getEmail()));
        }

        return "stockRadio";
    }

    @GetMapping("/stock/radio/search")
    public String clientRadioSearch(@RequestParam("searchWord") String searchWord, @LoginUser SessionUser user, Model model) {
        model.addAttribute("loginUser", user.getEmail());

        if (searchWord.equals("")) {
            model.addAttribute("productMap", this.stockService.removeUndefineStock(this.stockService.stockList(user.getEmail()), user.getEmail()));
        }
        else {
            model.addAttribute("productMap", this.stockService.removeUndefineStock(this.stockService.stockSearch(searchWord, user.getEmail()), user.getEmail()));
        }

        return "stockRadio";
    }
}
