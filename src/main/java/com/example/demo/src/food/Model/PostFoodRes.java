package com.example.demo.src.food.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor
public class PostFoodRes {
    private int foodInx;
//    해당 부분은 7주차 - JWT 수업 후 주석해제 및 대체해주세요!
//    private String jwt;
}
