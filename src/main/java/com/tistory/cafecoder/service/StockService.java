package com.tistory.cafecoder.service;

import com.tistory.cafecoder.domain.product.*;
import com.tistory.cafecoder.web.dto.NewestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StockService {
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    @Transactional
    public Long newestSave(NewestDto newestDto) {
        if (this.productRepository.findByName(newestDto.getName()) != null) {
            return -1L;
        } else {
            for (String color : newestDto.getColor()) {
                if (this.colorRepository.findByColor(color) == null) {
                    this.colorRepository.save(new Color().builder()
                            .color(color)
                            .build());
                }
            }

            for (String size : newestDto.getSize()) {
                if (this.sizeRepository.findBySize(size) == null) {
                    this.sizeRepository.save(new Size().builder()
                            .size(size)
                            .build());
                }
            }

            for (String color : newestDto.getColor()) {
                for (String size : newestDto.getSize()) {
                    this.productRepository.save(new Product().builder()
                            .clientId(newestDto.getClientId())
                            .name(newestDto.getName())
                            .colorId(this.colorRepository.findByColor(color).getId())
                            .sizeId(this.sizeRepository.findBySize(size).getId())
                            .build());
                }
            }

            return 1L;
        }
    }
}
