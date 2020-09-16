package com.tistory.cafecoder.web;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.service.StockService;
import com.tistory.cafecoder.web.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class XlsxController {

    private final StockService stockService;

    @GetMapping("/xlsx/result")
    public String orderList (Model model, @LoginUser SessionUser user, HttpServletRequest request) {
        model.addAttribute("loginUser", user.getEmail());
        HttpSession session = request.getSession();

        Map<String, List<OrderDto>> orderMap = (Map<String, List<OrderDto>>) session.getAttribute(user.getEmail());
        Set<Map.Entry<String, List<OrderDto>>> orderSet = orderMap.entrySet();

        model.addAttribute("orderAnalyze", orderSet);

        return "xlsxResult";
    }

    @GetMapping("/xlsx/result/deduction")
    public String deductionResult (@LoginUser SessionUser user, Model model, HttpServletRequest request) {
        model.addAttribute("loginUser", user.getEmail());

        HttpSession session = request.getSession();

        Map<String, List<OrderDto>> orderMap = (Map<String, List<OrderDto>>) session.getAttribute(user.getEmail());
        Set<Map.Entry<String, List<OrderDto>>> orderSet = this.stockService.getDeductionResult(orderMap).entrySet();

        model.addAttribute("orderAnalyze", orderSet);
        model.addAttribute("deducted", true);

        return "xlsxResult";
    }

    @GetMapping("/xlsx/filesup")
    public String filesUp (Model model, @LoginUser SessionUser user) {
        model.addAttribute("loginUser", user.getEmail());

        return "xlsx";
    }
}
