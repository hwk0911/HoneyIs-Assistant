
package com.tistory.cafecoder.service;

import com.tistory.cafecoder.config.xlsx.XlsxAnalyzer;
import com.tistory.cafecoder.config.xlsx.dto.XlsxDto;
import com.tistory.cafecoder.domain.product.*;
import com.tistory.cafecoder.web.dto.AmountDto;
import com.tistory.cafecoder.web.dto.OrderDto;
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
    private final ProductLinkRepository productLinkRepository;

    @Transactional(readOnly = true)
    public Map<Long, AmountDto> getResult(List<MultipartFile> multipartFiles, String email) {
        Map<Long, AmountDto> productMap = new HashMap<>();

        XlsxAnalyzer xlsxAnalyzer = new XlsxAnalyzer(multipartFiles);
        List<XlsxDto> analyzeList = xlsxAnalyzer.analyzer();

        AmountDto amountDto;

        for (XlsxDto orderProduct : analyzeList) {
            Long colorId;
            String productName = orderProduct.getProductName();

            if (this.colorRepository.findByColor(orderProduct.getColor()) == null) {
                colorId = this.colorRepository.save(new Color().builder().color(orderProduct.getColor()).build()).getId();
            } else {
                colorId = this.colorRepository.findByColor(orderProduct.getColor()).getId();
            }

            Long productId;
            Long stockAmount = 0L;

            if (this.sizeRepository.findBySize("FREE") == null) {
                this.sizeRepository.save(new Size().builder().size("FREE").build());
            }

            if (this.clientRepository.findByName(email) == null) {
                this.clientRepository.save(new Client().builder()
                        .email(email)
                        .name(email)
                        .location("")
                        .number("")
                        .build()).getId();
            }

            if(!this.productLinkRepository.findByProductNameAndEmail(orderProduct.getProductName(), email).isEmpty()) {
                productName = this.productLinkRepository.findByProductNameAndEmail(orderProduct.getProductName(), email).get(0).getTargetName();
            }

            if (this.productRepository.findByNameAndColorIdAndEmail(productName, colorId, email) == null) {
                if (!this.productRepository.findByNameAndEmail(productName, email).isEmpty()) {
                    Product sameProduct = this.productRepository.findByNameAndEmail(productName, email).get(0);

                    productId = this.productRepository.save(new Product().builder()
                            .name(productName)
                            .email(email)
                            .colorId(colorId)
                            .sizeId(this.sizeRepository.findBySize("FREE").getId())
                            .clientId(sameProduct.getClientId())
                            .build())
                            .getId();
                } else {
                    productId = this.productRepository.save(new Product().builder()
                            .name(productName)
                            .email(email)
                            .colorId(colorId)
                            .sizeId(this.sizeRepository.findBySize("FREE").getId())
                            .clientId(this.clientRepository.findByName(email).getId())
                            .build())
                            .getId();
                }
            } else {
                Product stock = this.productRepository.findByNameAndColorIdAndEmail(productName, colorId, email);

                productId = stock.getId();
                stockAmount = stock.getAmount();
            }

            if (productMap.containsKey(productId)) {
                amountDto = productMap.get(productId);
            }
            else {
                amountDto = new AmountDto(0L, stockAmount);
            }

            amountDto.addOrderAmount(orderProduct.getAmount());

            productMap.put(productId, amountDto);
        }

        return productMap;
    }


    @Transactional(readOnly = true)
    public Map<String, List<OrderDto>> groupByClient(Map<Long, AmountDto> productMap, String email) {
        Map<String, List<OrderDto>> groupResult = new HashMap<>();
        List<OrderDto> tempProduct;

        Iterator<Long> productIterator = productMap.keySet().iterator();

        while (productIterator.hasNext()) {
            Product product = this.productRepository.findById(productIterator.next()).get();
            String client;

            if (product.getClientId() == null) {
                client = email;
            } else {
                try {
                    client = this.clientRepository.findByIdAndEmail(product.getClientId(), email).getName();
                } catch (NullPointerException e) {
                    client = this.clientRepository.findByName(email).getName();
                }
            }

            if (groupResult.containsKey(client)) {
                tempProduct = groupResult.get(client);
            } else {
                tempProduct = new ArrayList<>();
            }

            OrderDto productDto = new OrderDto(product.getId(),
                    product.getName(),
                    this.colorRepository.findById(product.getColorId()).get().getColor(),
                    this.sizeRepository.findById(product.getSizeId()).get().getSize(),
                    productMap.get(product.getId()).getOrderAmount(),
                    productMap.get(product.getId()).getStockAmount());

            tempProduct.add(productDto);
            groupResult.put(client, tempProduct);
        }

        return this.sort(groupResult);
    }

    private Map<String, List<OrderDto>> sort(Map<String, List<OrderDto>> groupResult) {
        Iterator<String> mapItr = groupResult.keySet().iterator();

        while (mapItr.hasNext()) {
            String key = mapItr.next();
            List<OrderDto> temp = groupResult.get(key);

            Collections.sort(temp, new Comparator<OrderDto>() {

                @Override
                public int compare(OrderDto o1, OrderDto o2) {
                    return o1.getProductName().compareTo(o2.getProductName());
                }
            });

            groupResult.put(key, temp);
        }

        return groupResult;
    }
}
