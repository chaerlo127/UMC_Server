package com.example.demo.src.food;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.food.Model.GetFoodRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/foods")

public class FoodController {
    @Autowired
    private final FoodProvider foodprovider;
    @Autowired
    private final FoodService foodService;
    @Autowired
    private final JwtService jwtService;


    public FoodController(FoodProvider foodprovider, FoodService foodService, JwtService jwtService) {
        this.foodprovider = foodprovider;
        this.foodService = foodService;
        this.jwtService = jwtService;
    }



    //foodinx에 따른 값 불러오기
    @ResponseBody
    @GetMapping("/{foodInx}") // (GET) 127.0.0.1:9000/app/foods/:foodInx
    public BaseResponse<GetFoodRes> getRep(@PathVariable("foodInx") int foodInx) {
        try {
            GetFoodRes getFoodRes = foodprovider.getFood(foodInx);
            return new BaseResponse<>(getFoodRes);
        } catch (BaseException e){
            return new BaseResponse<>((e.getStatus()));
        }

    }
}
