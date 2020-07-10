package com.tistory.cafecoder.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  Role {
    USER("ROLE_USER", "일반 사용자"),
    MASTER("ROLE_MASTER", "관리자");

    private final String key;
    private final String title;

}
