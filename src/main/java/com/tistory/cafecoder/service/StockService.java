package com.tistory.cafecoder.service;

import com.tistory.cafecoder.domain.product.*;
import com.tistory.cafecoder.web.dto.NewestDto;
import com.tistory.cafecoder.web.dto.ProductDto;
import com.tistory.cafecoder.web.dto.UndefinedStockDto;
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
    public Long newestSave(NewestDto newestDto, String email) {
        String[] colorArr = newestDto.getColor().split(",");
        String[] sizeArr = newestDto.getSize().split(",");

        if (!this.productRepository.findByName(newestDto.getName()).isEmpty()) {
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
                            .email(email)
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

        for (ClientMapping clientMapping : clientIdList) {
            Long clientId = clientMapping.getId();
            String clientName = clientMapping.getName();
            List<Product> productList = this.productRepository.findByClientId(clientId);

            for (Product product : productList) {
                List<ProductDto> valueList;

                if (retMap.containsKey(clientName)) {
                    valueList = retMap.get(clientName);
                } else {
                    valueList = new ArrayList<>();
                }

                valueList.add(new ProductDto(
                        product.getId(),
                        product.getName(),
                        this.colorRepository.findColorById(product.getColorId()).getColor(),
                        this.sizeRepository.findSizeById(product.getSizeId()).getSize(),
                        product.getAmount()));

                retMap.put(clientName, valueList);
            }
        }

        return retMap.entrySet();
    }

    @Transactional
    public Long stockUpdate(ProductDto updateDto) {
        Long colorId;
        Long sizeId;

        if (this.colorRepository.findByColor(updateDto.getColor()) == null) {
            colorId = this.colorRepository.save(new Color().builder().color(updateDto.getColor()).build()).getId();
        } else {
            colorId = this.colorRepository.findByColor(updateDto.getColor()).getId();
        }

        if (this.sizeRepository.findBySize(updateDto.getSize()) == null) {
            sizeId = this.sizeRepository.save(new Size().builder().size(updateDto.getSize()).build()).getId();
        } else {
            sizeId = this.sizeRepository.findBySize(updateDto.getSize()).getId();
        }

        Product product = this.productRepository.findById(updateDto.getId()).get();

        product.update(
                updateDto.getProductName(),
                this.colorRepository.findByColor(updateDto.getColor()).getId(),
                this.sizeRepository.findBySize(updateDto.getSize()).getId(),
                updateDto.getAmount(),
                product.getClientId()
        );

        return product.getId();
    }

    @Transactional
    public Long stockDelete(Long id) {
        Product product = this.productRepository.findById(id).get();

        this.productRepository.delete(product);

        return 1L;
    }

    @Transactional(readOnly = true)
    public HashSet<String> undefinedStock(String email) {
        if(this.clientRepository.findByName(email) == null) {
            return null;
        }
        else {
            List<Product> undefinedStockList = this.productRepository.findByClientId(this.clientRepository.findByName(email).getId());
            return this.setProductDto(undefinedStockList);
        }
    }

    public HashSet<String> setProductDto(List<Product> productList) {
        HashSet<String> undefinedSet = new HashSet<>();

        for (Product product : productList) {
            undefinedSet.add(product.getName());
        }

        return undefinedSet;
    }

    @Transactional
    public Long clientAllModify (UndefinedStockDto undefinedStockDto, String email) {
        Long clientId = this.clientRepository.findByName(email).getId();
        Long newClientId = this.clientRepository.findByName(undefinedStockDto.getClientName()).getId();

        List<Product> productList = this.productRepository.findByNameAndClientId(undefinedStockDto.getProductName(), clientId);

        System.out.println(clientId);
        System.out.println(newClientId);
        System.out.println(productList.size());

        System.out.println(undefinedStockDto.getProductName());

        for(Product product : productList) {
            product.update(
                    product.getName(),
                    product.getColorId(),
                    product.getSizeId(),
                    product.getAmount(),
                    newClientId
            );
        }

        return productList.get(0).getId();
    }

    public Set<Map.Entry<String, List<ProductDto>>> stockSearch (String searchWord, String email) {
        Map<String, List<ProductDto>> retMap = new HashMap<>();

        List<Product> searchResult = this.productRepository.findByNameContainsAndEmail(searchWord, email);
        List<ProductDto> tempProductDtoList;

        for(Product product : searchResult) {
            String clientName = this.clientRepository.findById(product.getClientId()).get().getName();

            if(retMap.containsKey(clientName)) {
                tempProductDtoList = retMap.get(clientName);
            }
            else {
                tempProductDtoList = new ArrayList<>();
            }

            tempProductDtoList.add(new ProductDto(
                    product.getId(),
                    product.getName(),
                    this.colorRepository.findById(product.getColorId()).get().getColor(),
                    this.sizeRepository.findById(product.getSizeId()).get().getSize(),
                    product.getAmount()
            ));

            retMap.put(clientName, tempProductDtoList);
        }

        return retMap.entrySet();
    }

    public Set<Map.Entry<String, List<ProductDto>>> stockClientSearch (String searchWord, String email) {
        Map<String, List<ProductDto>> retMap = new HashMap<>();

        List<Client> searchResult = this.clientRepository.findByNameContainsAndEmail(searchWord, email);
        List<ProductDto> tempProductDtoList;

        for(Client client : searchResult) {
            tempProductDtoList = new ArrayList<>();

            for(Product product : this.productRepository.findByClientId(client.getId())) {
                tempProductDtoList.add(new ProductDto(
                        product.getId(),
                        product.getName(),
                        this.colorRepository.findById(product.getColorId()).get().getColor(),
                        this.sizeRepository.findById(product.getSizeId()).get().getSize(),
                        product.getAmount()
                ));
            }

            retMap.put(client.getName(), tempProductDtoList);
        }

        return retMap.entrySet();
    }
}
