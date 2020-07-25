package com.tistory.cafecoder.service;

import com.tistory.cafecoder.domain.expenditure.Expenditure;
import com.tistory.cafecoder.domain.product.Client;
import com.tistory.cafecoder.domain.product.ClientRepository;
import com.tistory.cafecoder.web.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public Long create(ClientDto clientDto) {
        Client client = clientDto.toEntity();

        return this.clientRepository.save(client).getId();
    }

    @Transactional(readOnly = true)
    public List<Client> searchAll() {
        return this.clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Client search(String searchWord) {
        return this.clientRepository.findByName(searchWord);
    }

    @Transactional
    public Long update(ClientDto clientDto) {
        Client client = findById(clientDto.getId());

        if(client != null) {
            client.update(clientDto.getClientName(), clientDto.getNumber(), clientDto.getLocation());
        }

        return client.getId();
    }

    @Transactional
    public Long delete(Long id) {
        Client client = findById(id);

        this.clientRepository.delete(client);

        return id;
    }

    public Client findById (Long id) {
        Client client = this.clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("지출내역 조회 중 오류가 발생하였습니다. id: " + id));

        return client;
    }
}
