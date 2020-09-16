package com.tistory.cafecoder.config.money;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MoneyTrans {
    private String money;

    public MoneyTrans(Long money) {
        StringBuilder sb = new StringBuilder(money + "");
        StringBuilder retMoney = new StringBuilder();

        for(int index = 0, size = sb.length() ; index < size; ++index) {
            retMoney.append(sb.charAt(index));
            switch (size - index - 1) {
                case 12:
                    retMoney.append("조 ");
                    break;
                case 8:
                    retMoney.append("억 ");
                    break;
                case 4:
                    retMoney.append("만 ");
                    break;
            }
        }

        this.money = retMoney.append("원").toString();
    }
}
