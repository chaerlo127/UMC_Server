package com.example.demo.src.food.Model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteFoodReq {
    private int foodInx;
}
