package com.tistory.cafecoder.config.auth.dto;

import com.tistory.cafecoder.domain.user.User;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;
    private Set<Map.Entry<String, Integer>> sendList;
    private Set<Map.Entry<String, Integer>> orderList;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

    public SessionUser setSendList (Set<Map.Entry<String, Integer>> sendList) {
        this.sendList = sendList;

        return this;
    }

    public SessionUser setOrderList (Set<Map.Entry<String, Integer>> orderList) {
        this.orderList = orderList;

        return this;
    }
}
