package com.tistory.cafecoder.web.apiController;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.service.StockService;
import com.tistory.cafecoder.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class StockApiController {

    private final StockService stockService;

    @PostMapping("/api/v1/stock/newest")
    public Long newest(@RequestBody NewestDto newestDto, @LoginUser SessionUser user) {
        return this.stockService.newestSave(newestDto, user.getEmail());
    }

    @PutMapping("/api/v1/stock/update")
    public Long stockUpdate (@RequestBody ProductDto productDto) {
        return this.stockService.stockUpdate(productDto);
    }

    @DeleteMapping("/api/v1/stock/delete/{id}")
    public Long stockDelete (@PathVariable Long id) {
        return this.stockService.stockDelete(id);
    }

    @PutMapping("/api/v1/stock/client/update")
    public Long clientModify (@RequestBody UndefinedStockDto undefinedStockDto, @LoginUser SessionUser user) {
        return this.stockService.clientAllModify(undefinedStockDto, user.getEmail());
    }

    @PostMapping("/api/v1/stock/link/update")
    public Long linkSave (@RequestBody ProductLinkDto productLinkDto, @LoginUser SessionUser user) {
        return this.stockService.linkSave(productLinkDto, user.getEmail());
    }

    @DeleteMapping("/api/v1/stock/undefined/delete/{productName}")
    public Long undefinedDelete (@PathVariable("productName") String productName) {
        return this.stockService.stockDelete(productName);
    }

    @PutMapping("/api/v1/stock/deduction")
    public Long stockDeduction (@RequestBody Map<String, Object> deductionMap) {
        return this.stockService.deduction(deductionMap);
    }
}
