package com.tistory.cafecoder.web.apiController;

import com.tistory.cafecoder.service.StockService;
import com.tistory.cafecoder.web.dto.NewestDto;
import com.tistory.cafecoder.web.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class StockApiController {

    private final StockService stockService;

    @PostMapping("/api/v1/stock/newest")
    public Long newest(@RequestBody NewestDto newestDto) {
        return this.stockService.newestSave(newestDto);
    }

    @PutMapping("/api/v1/stock/update")
    public Long stockUpdate (@RequestBody ProductDto productDto) {
        return this.stockService.stockUpdate(productDto);
    }

    @DeleteMapping("/api/v1/stock/delete/{id}")
    public Long stockDelete (@PathVariable Long id) {
        return this.stockService.stockDelete(id);
    }
}
