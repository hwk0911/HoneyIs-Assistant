package com.tistory.cafecoder.web;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.web.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class XlsxController {
    @GetMapping("/xlsx/result/{method}")
    public String orderList (Model model, @LoginUser SessionUser user, @PathVariable("method") String method, HttpServletRequest request) {
        model.addAttribute("loginUser", user.getEmail());
        HttpSession session = request.getSession();

        Map<String, List<ProductDto>> orderMap = (Map<String, List<ProductDto>>) session.getAttribute(user.getEmail());
        Set<Map.Entry<String, List<ProductDto>>> orderSet = orderMap.entrySet();

        model.addAttribute("orderAnalyze", orderSet);

        return "xlsxResult";
    }

    @GetMapping("/xlsx/filesup")
    public String filesUp (Model model, @LoginUser SessionUser user) {
        model.addAttribute("loginUser", user.getEmail());

        return "xlsx";
    }
}
