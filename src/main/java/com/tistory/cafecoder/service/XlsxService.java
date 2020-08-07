
package com.tistory.cafecoder.service;

import com.tistory.cafecoder.config.xlsx.XlsxAnalyzer;
import com.tistory.cafecoder.domain.product.ColorRepository;
import com.tistory.cafecoder.domain.product.ProductRepository;
import com.tistory.cafecoder.domain.product.SizeRepository;
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

    @Transactional(readOnly = true)
    public List<Map<String, Integer>> getResult (List<MultipartFile> multipartFiles) {
        List<Map<String, Integer>> dataResult = new ArrayList<>();

        Map<String, Integer> foundProductMap = new HashMap<>();
        Map<String, Integer> notFoundProductMap = new HashMap<>();

        XlsxAnalyzer xlsxAnalyzer = new XlsxAnalyzer(multipartFiles);
        Map<String, Integer> dataMap = xlsxAnalyzer.analyzer();

        Iterator<String> dataMapIterator = dataMap.keySet().iterator();

        while (dataMapIterator.hasNext()) {
            String data = dataMapIterator.next();
            String[] splitData = data.split("-");
            Integer amount = dataMap.get(data);

            Long productId = this.productRepository.findByName(splitData[0]).getId();
            Long colorId = this.colorRepository.findByColor(splitData[1]).getId();
            Long sizeId = this.sizeRepository.findBySize(splitData[2]).getId();

            if(productId == null || colorId == null || sizeId == null) {
                notFoundProductMap.put(data, amount);
            }
            else {
                foundProductMap.put(new StringBuilder(productId + "-" + colorId + "-" + sizeId).toString(), amount);
            }
        }

        dataResult.add(foundProductMap);
        dataResult.add(notFoundProductMap);

        return dataResult;
    }
}
