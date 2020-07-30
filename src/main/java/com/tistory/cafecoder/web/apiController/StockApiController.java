package com.tistory.cafecoder.web.apiController;

import com.tistory.cafecoder.service.StockService;
import com.tistory.cafecoder.web.dto.NewestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StockApiController {

    private final StockService stockService;

    @PostMapping("/api/v1/stock/newest")
    public Long newest(@RequestBody NewestDto newestDto) {
        System.out.println(newestDto.getName());

        return this.stockService.newestSave(newestDto);
    }
}
