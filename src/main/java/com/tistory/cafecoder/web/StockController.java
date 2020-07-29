package com.tistory.cafecoder.web;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class StockController {

    @PostMapping("/stock")
    public String stockMain(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("user", user.getEmail());
        }

        return "stock";
    }
}
