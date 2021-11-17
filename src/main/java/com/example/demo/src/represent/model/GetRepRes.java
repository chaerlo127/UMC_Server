package com.example.demo.src.represent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor
public class GetRepRes {
    private int repInx;
    private String name;
    private String repId;
    private String password;
    private int min_price;
    private String address;
    private String close_time;
    private String phone;
    private int foodInx;
}
