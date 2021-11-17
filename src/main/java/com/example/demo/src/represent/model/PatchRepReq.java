package com.example.demo.src.represent.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchRepReq {
    private int repInx;
    private int min_price;
}
