package com.tistory.cafecoder.web;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class XlsxController {
    @GetMapping("/xlsx/result/{method}")
    public String orderList (Model model, @LoginUser SessionUser user, @PathVariable("method") String method) {
        model.addAttribute("user", user.getEmail());

        model.addAttribute("xlsxResult", user.getXlsxResult());

        return "xlsxResult";
    }

    @GetMapping("/xlsx/filesup")
    public String filesUp (Model model, @LoginUser SessionUser user) {
        model.addAttribute("user", user.getEmail());

        return "xlsx";
    }
}
