package com.example.demo.src.food;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.food.Model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.GET_FOODS_DONT_HAVE_FOODNAME;
import static com.example.demo.config.BaseResponseStatus.POST_FOODS_EMPTY_NAME;


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

    //food 추가히기 users/sign-up 과 같은 내용
    @ResponseBody
    @PostMapping("/addfood")
    public BaseResponse<PostFoodRes> createFood(@RequestBody PostFoodReq postFoodReq){
        if(postFoodReq.getFoodName() == null){
            return new BaseResponse<>(POST_FOODS_EMPTY_NAME);
        }
        try{
            PostFoodRes postFoodRes = foodService.createFood(postFoodReq);
            return new BaseResponse<>(postFoodRes);

        } catch (BaseException e){
            return new BaseResponse<>((e.getStatus()));
        }
    }

    /**
     * food 정보 변경 API
     * [PATCH] /foods/:foodInx
     */
    //foodName 이름 변경
//최소 주문금액 변경
    @ResponseBody
    @PatchMapping("/{foodInx}")
    public BaseResponse<String> modifyFoodName(@PathVariable("foodInx") int foodInx, @RequestBody Food food) {
        try {
/**
 *********** 해당 부분은 7주차 - JWT 수업 후 주석해체 해주세요!  ****************
 //jwt에서 idx 추출.
 int userIdxByJwt = jwtService.getUserIdx();
 //userIdx와 접근한 유저가 같은지 확인
 if(userIdx != userIdxByJwt){
 return new BaseResponse<>(INVALID_USER_JWT);
 }
 //같다면 유저네임 변경
 **************************************************************************
 */
            PatchFoodReq patchFoodReq = new PatchFoodReq(foodInx, food.getFoodName());
            foodService.modifyFoodName(patchFoodReq);

            String result = "FoodName 정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //Query String
    @ResponseBody
    @GetMapping("")
    // GET 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<List<GetFoodRes>> getUsers(@RequestParam(required = false) String foodName) {
        //  @RequestParam은, 1개의 HTTP Request 파라미터를 받을 수 있는 어노테이션(?뒤의 값). default로 RequestParam은 반드시 값이 존재해야 하도록 설정되어 있지만, (전송 안되면 400 Error 유발)
        //  지금 예시와 같이 required 설정으로 필수 값에서 제외 시킬 수 있음
        //  defaultValue를 통해, 기본값(파라미터가 없는 경우, 해당 파라미터의 기본값 설정)을 지정할 수 있음
        try {
            if (foodName != null) { // query string인 nickname이 없을 경우, 그냥 전체 유저정보를 불러온다.
                return new BaseResponse<>(GET_FOODS_DONT_HAVE_FOODNAME);
            }
            List<GetFoodRes> getFoodRes = foodprovider.getFoods();
            return new BaseResponse<>(getFoodRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @DeleteMapping("/{foodInx}")
    public BaseResponse<String> DeleteFood(@PathVariable("foodInx") int foodInx) {
        try {
/**
 *********** 해당 부분은 7주차 - JWT 수업 후 주석해체 해주세요!  ****************
 //jwt에서 idx 추출.
 int userIdxByJwt = jwtService.getUserIdx();
 //userIdx와 접근한 유저가 같은지 확인
 if(userIdx != userIdxByJwt){
 return new BaseResponse<>(INVALID_USER_JWT);
 }
 //같다면 유저네임 변경
 **************************************************************************
 */
            DeleteFoodReq deleteFoodReq = new DeleteFoodReq(foodInx);
            foodService.deleteFoodName(deleteFoodReq);

            String result = "FoodName 정보가 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
