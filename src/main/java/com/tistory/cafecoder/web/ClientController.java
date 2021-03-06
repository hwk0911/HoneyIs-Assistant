package com.tistory.cafecoder.web;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/client")
    public String client(Model model, @LoginUser SessionUser user) {

        if (user != null) {
            model.addAttribute("loginUser", user.getEmail());
            model.addAttribute("clientList", this.clientService.searchAll(user.getEmail()));
        }

        return "client";
    }

    @GetMapping("/client/search")
    public String clientSearch(@RequestParam("searchWord") String searchWord, @LoginUser SessionUser user, Model model) {
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

        return "client";
    }

    @GetMapping("/client/radio")
    public String searchClientPopup (Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("loginUser", user.getEmail());
            model.addAttribute("clientList", this.clientService.searchAll(user.getEmail()));
        }

        return "clientRadio";
    }

    @GetMapping("/client/radio/search")
    public String clientRadioSearch(@RequestParam("searchWord") String searchWord, @LoginUser SessionUser user, Model model) {
        model.addAttribute("loginUser", user.getEmail());

        if (searchWord.equals("")) {
            model.addAttribute("clientList", this.clientService.searchAll(user.getEmail()));
        }
        else {
            model.addAttribute("clientList", this.clientService.search(searchWord, user.getEmail()));
        }

        return "clientRadio";
    }
}
