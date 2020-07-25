package com.tistory.cafecoder.web;

import com.tistory.cafecoder.service.ClientService;
import com.tistory.cafecoder.web.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ClientApiController {

    private final ClientService clientService;

    @PostMapping("/client/save")
    public Long save(@RequestBody ClientDto clientDto) {
        return this.clientService.create(clientDto);
    }

    @PutMapping("/client/update")
    public Long update(@RequestBody ClientDto clientDto) {
        return this.clientService.update(clientDto);
    }

    @DeleteMapping("/client/delete/{id}")
    public Long delete(@PathVariable("id") Long id) {
        return this.clientService.delete(id);
    }
}
