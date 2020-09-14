package com.tistory.cafecoder.web.apiController;

import com.tistory.cafecoder.config.auth.LoginUser;
import com.tistory.cafecoder.config.auth.dto.SessionUser;
import com.tistory.cafecoder.service.ClientService;
import com.tistory.cafecoder.web.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ClientApiController {

    private final ClientService clientService;

    @PostMapping("/api/v1/client/save")
    public Long save(@RequestBody ClientDto clientDto) {
        System.out.println(clientDto.getEmail() + " " + clientDto.getName() + " " + clientDto.getLocation() + " " + clientDto.getNumber());

        return this.clientService.create(clientDto);
    }

    @PutMapping("/api/v1//client/update")
    public Long update(@RequestBody ClientDto clientDto) {
        return this.clientService.update(clientDto);
    }

    @DeleteMapping("/api/v1/client/delete/{id}")
    public Long delete(@PathVariable("id") Long id, @LoginUser SessionUser user) {
        return this.clientService.delete(id, user.getEmail());
    }
}
