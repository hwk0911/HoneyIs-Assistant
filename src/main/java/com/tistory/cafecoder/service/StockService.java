package com.tistory.cafecoder.service;

import com.tistory.cafecoder.domain.product.*;
import com.tistory.cafecoder.web.dto.NewestDto;
import com.tistory.cafecoder.web.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class StockService {
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public Long newestSave(NewestDto newestDto) {
        String[] colorArr = newestDto.getColor().split(",");
        String[] sizeArr = newestDto.getSize().split(",");

        if (this.productRepository.findByName(newestDto.getName()) != null) {
            return -1L;
        } else {
            for (String color : colorArr) {
                if (this.colorRepository.findByColor(color) == null) {
                    this.colorRepository.save(new Color().builder()
                            .color(color)
                            .build());
                }
            }

            for (String size : sizeArr) {
                if (this.sizeRepository.findBySize(size) == null) {
                    this.sizeRepository.save(new Size().builder()
                            .size(size)
                            .build());
                }
            }

            for (String color : colorArr) {
                for (String size : sizeArr) {
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

    @Transactional(readOnly = true)
    public Set<Map.Entry<String, List<ProductDto>>> stockList(String email) {
        List<ClientMapping> clientIdList = this.clientRepository.findIdByEmail(email);

        Map<String, List<ProductDto>> retMap = new HashMap<>();

        for(ClientMapping clientMapping : clientIdList) {
            Long clientId = clientMapping.getId();
            String clientName = clientMapping.getName();
            List<Product> productList = this.productRepository.findByClientId(clientId);

            for(Product product : productList) {
                List<ProductDto> valueList;

                if(retMap.containsKey(clientName)) {
                    valueList = retMap.get(clientName);
                }
                else {
                    valueList = new ArrayList<>();
                }

                valueList.add(new ProductDto(
                        product.getName(),
                        this.colorRepository.findColorById(product.getColorId()).getColor(),
                        this.sizeRepository.findSizeById(product.getSizeId()).getSize(),
                        product.getAmount()));

                retMap.put(clientName, valueList);
            }
        }

        return retMap.entrySet();
    }

}
