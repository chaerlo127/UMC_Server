package com.example.demo.src.represent.model;

import lombok.*;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(userIdx, nickname, email, password)를 받는 생성자를 생성
public class Represent {

    private int repInx;
    private String name;
    private String repId;
    private String password;
    private int min_price;
    private String address;
    private String close_time;
    private String phone;
    private String close_date;
    private String foodInx;


}
