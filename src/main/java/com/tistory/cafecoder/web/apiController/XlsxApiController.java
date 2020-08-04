package com.tistory.cafecoder.web.apiController;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.service.XlsxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class XlsxApiController {
    private final XlsxService xlsxService;

    @PostMapping("/api/v1/xlsx/analyze/{method}")
    public String setOrder(@RequestParam(value = "files") List<MultipartFile> multipartFiles, @PathVariable("method") String method, @LoginUser SessionUser user) {
        System.out.println(multipartFiles.size());

        if (method.equals("order")) {
            return user.setOrderList(this.xlsxService.getResult(multipartFiles)).getEmail();
        } else {
            return user.setSendList(this.xlsxService.getResult(multipartFiles)).getEmail();
        }
    }
}
