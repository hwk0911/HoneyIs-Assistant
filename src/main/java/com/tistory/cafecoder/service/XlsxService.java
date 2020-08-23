
package com.tistory.cafecoder.service;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.config.xlsx.XlsxAnalyzer;
import com.tistory.cafecoder.config.xlsx.dto.XlsxDto;
import com.tistory.cafecoder.domain.product.*;
import com.tistory.cafecoder.web.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RequiredArgsConstructor
@Service
public class XlsxService {
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Map<Long, Long> getResult(List<MultipartFile> multipartFiles) {
        Map<Long, Long> productMap = new HashMap<>();

        XlsxAnalyzer xlsxAnalyzer = new XlsxAnalyzer(multipartFiles);
        List<XlsxDto> analyzeList = xlsxAnalyzer.analyzer();

        for (XlsxDto product : analyzeList) {
            Long colorId;

            if(this.colorRepository.findByColor(product.getColor()) == null) {
                colorId = this.colorRepository.save(new Color().builder().color(product.getColor()).build()).getId();
            }
            else {
                colorId = this.colorRepository.findByColor(product.getColor()).getId();
            }

            Long productId;

            if(this.sizeRepository.findBySize("FREE") == null) {
                this.sizeRepository.save(new Size().builder().size("FREE").build());
            }

            if(this.productRepository.findByNameAndColorId(product.getProductName(), colorId) == null) {
                productId = this.productRepository.save(new Product().builder()
                        .name(product.getProductName())
                        .colorId(colorId)
                        .sizeId(this.sizeRepository.findBySize("FREE").getId())
                        .clientId(null)
                        .build())
                        .getId();
            }
            else {
                productId = this.productRepository.findByNameAndColorId(product.getProductName(), colorId).getId();
            }

            Long amount = product.getAmount();

            if(productMap.containsKey(productId)) {
                amount += productMap.get(productId);
            }

            productMap.put(productId, amount);
        }

        return productMap;
    }

    //todo: 1차로 주문 목록을 보여주고, 후에 웹페이지 이동을 통해 비교를 시작하도록 수정

    @Transactional(readOnly = true)
    public Map<String, List<ProductDto>> groupByClient (Map<Long, Long> productMap, String email) {
        Map<String, List<ProductDto>> groupResult = new HashMap<>();
        List<ProductDto> tempProduct;

        Iterator<Long> productIterator = productMap.keySet().iterator();

        while (productIterator.hasNext()) {
            Product product = this.productRepository.findById(productIterator.next()).get();
            String client;

            if(product.getClientId() == null) {
                client = "발주처 없음";
            }
            else {
                try {
                    client = this.clientRepository.findByIdAndEmail(product.getClientId(), email).getName();
                }
                catch (NullPointerException e) {
                    client = "발주처 없음";
                }
            }

            if(groupResult.containsKey(client)) {
                tempProduct = groupResult.get(client);
            }
            else {
                tempProduct = new ArrayList<>();
            }

            ProductDto productDto = new ProductDto(product.getId(),
                    product.getName(),
                    this.colorRepository.findById(product.getColorId()).get().getColor(),
                    this.sizeRepository.findById(product.getSizeId()).get().getSize(),
                    productMap.get(product.getId()));

            tempProduct.add(productDto);
            groupResult.put(client, tempProduct);
        }

        return this.sort(groupResult);
    }

    private Map<String, List<ProductDto>> sort (Map<String, List<ProductDto>> groupResult) {
        Iterator<String> mapItr = groupResult.keySet().iterator();

        while (mapItr.hasNext()) {
            String key = mapItr.next();
            List<ProductDto> temp = groupResult.get(key);

            Collections.sort(temp, new Comparator<ProductDto>() {

                @Override
                public int compare(ProductDto o1, ProductDto o2) {
                    return o1.getProductName().compareTo(o2.getProductName());
                }
            });

            groupResult.put(key, temp);
        }

        return groupResult;
    }
}
