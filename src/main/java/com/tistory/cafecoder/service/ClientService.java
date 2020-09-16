package com.tistory.cafecoder.service;

import com.tistory.cafecoder.domain.product.Client;
import com.tistory.cafecoder.domain.product.ClientRepository;
import com.tistory.cafecoder.domain.product.Product;
import com.tistory.cafecoder.domain.product.ProductRepository;
import com.tistory.cafecoder.web.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Long create(ClientDto clientDto, String email) {
        if(this.clientRepository.findByNameAndEmail(clientDto.getName(), email) != null) return 0L;

        Client client = clientDto.toEntity();
        return this.clientRepository.save(client).getId();
    }

    @Transactional(readOnly = true)
    public List<Client> searchAll(String email) {
        return this.removeDefault(this.clientRepository.findByEmail(email), email);
    }

    @Transactional(readOnly = true)
    public List<Client> search(String searchWord, String email) {
        return this.removeDefault(this.clientRepository.findByNameContainsAndEmail(searchWord, email), email);
    }

    @Transactional
    public Long update(ClientDto clientDto) {
        Client client = findById(clientDto.getId());

        if(client != null) {
            client.update(clientDto.getName(), clientDto.getNumber(), clientDto.getLocation());
        }

        return client.getId();
    }

    @Transactional
    public Long delete(Long id, String email) {
        Client client = findById(id);

        for(Product product : this.productRepository.findByClientId(id)) {
            product.update(
                    product.getName(),
                    product.getColorId(),
                    product.getSizeId(),
                    product.getAmount(),
                    this.clientRepository.findByName(email).getId()
            );
        }

        this.clientRepository.delete(client);

        return id;
    }

    public Client findById (Long id) {
        Client client = this.clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("지출내역 조회 중 오류가 발생하였습니다. id: " + id));

        return client;
    }

    public List<Client> removeDefault (List<Client> clientList, String email) {
        for(int index = 0, size = clientList.size() ; index < size ; ++index) {
            if(clientList.get(index).getName().equals(email)) {
                clientList.remove(index);

                return clientList;
            }
        }

        return clientList;
    }
}
