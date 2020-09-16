package com.tistory.cafecoder.web.apiController;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.service.XlsxService;
import com.tistory.cafecoder.web.dto.AmountDto;
import com.tistory.cafecoder.web.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class XlsxApiController {
    private final XlsxService xlsxService;

    @PostMapping("/api/v1/xlsx/analyze")
    public Long setOrder(@RequestParam(value = "files") List<MultipartFile> multipartFiles, @LoginUser SessionUser user, HttpServletRequest request) {
        Map<Long, AmountDto> productMap = this.xlsxService.getResult(multipartFiles, user.getEmail());
        Map<String, List<OrderDto>> groupResult = this.xlsxService.groupByClient(productMap, user.getEmail());

        HttpSession session = request.getSession();
        session.setAttribute(user.getEmail(), groupResult);

        return 1L;
    }
}
