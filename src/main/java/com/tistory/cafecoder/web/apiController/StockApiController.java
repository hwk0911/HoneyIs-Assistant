package com.tistory.cafecoder.web.apiController;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.service.StockService;
import com.tistory.cafecoder.web.dto.NewestDto;
import com.tistory.cafecoder.web.dto.ProductDto;
import com.tistory.cafecoder.web.dto.UndefinedStockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    
    //todo : 발주처 미정상품 업데이트용 Rest API 제작 + 서비스 메소드 추가

    @PutMapping("/api/v1/stock/client/update")
    public Long clientModify (@RequestBody UndefinedStockDto undefinedStockDto, @LoginUser SessionUser user) {
        return this.stockService.clientAllModify(undefinedStockDto, user.getEmail());
    }
}
