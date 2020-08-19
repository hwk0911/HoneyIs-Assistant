package com.tistory.cafecoder.web.apiController;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.domain.product.ProductRepository;
import com.tistory.cafecoder.service.XlsxService;
import com.tistory.cafecoder.web.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class XlsxApiController {
    private final XlsxService xlsxService;
    private final ProductRepository productRepository;

    @PostMapping("/api/v1/xlsx/analyze/{method}")
    public Long setOrder(@RequestParam(value = "files") List<MultipartFile> multipartFiles, @PathVariable("method") String method, @LoginUser SessionUser user) {
        Map<Long, Long> productMap = this.xlsxService.getResult(multipartFiles);

        user.setXlsxResult(this.xlsxService.groupByClient(productMap));

        return 1L;
    }
}
