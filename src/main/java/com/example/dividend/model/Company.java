package com.example.dividend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    //entity 와 model 을 구분하는 이유는 entity는 db와 연결하는 역할
    // model은 내부에서 사용하기 위함

    private String ticker;
    private String name;


}
