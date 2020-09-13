package com.tistory.cafecoder.config.auth.dto;

import com.tistory.cafecoder.domain.user.User;
import com.tistory.cafecoder.web.dto.ProductDto;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
