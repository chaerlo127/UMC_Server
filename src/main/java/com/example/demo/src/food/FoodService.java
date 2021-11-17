package com.example.demo.src.food;


import com.example.demo.config.BaseException;
import com.example.demo.src.food.Model.DeleteFoodReq;
import com.example.demo.src.food.Model.PatchFoodReq;
import com.example.demo.src.food.Model.PostFoodReq;
import com.example.demo.src.food.Model.PostFoodRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
public class FoodService {

    private final FoodProvider foodProvider;
    private final FoodDao foodDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FoodService(FoodProvider foodProvider, FoodDao foodDao, JwtService jwtService){
        this.foodProvider = foodProvider;
        this.foodDao = foodDao;
        this.jwtService = jwtService;
    }

    //FoodName 추가
    public PostFoodRes createFood(PostFoodReq postFoodReq) throws BaseException {
        // 중복 확인: 해당 이메일을 가진 유저가 있는지 확인합니다. 중복될 경우, 에러 메시지를 보냅니다.
        if (foodProvider.checkFoodName(postFoodReq.getFoodName()) == 1) {
            throw new BaseException(POST_FOODS_EXISTS_NAME);
        }
        try {
            int foodInx = foodDao.createFood(postFoodReq);
            return new PostFoodRes(foodInx);

//  *********** 해당 부분은 7주차 수업 후 주석해제하서 대체해서 사용해주세요! ***********
//            //jwt 발급.
//            String jwt = jwtService.createJwt(userIdx);
//            return new PostUserRes(jwt,userIdx);
//  *********************************************************************
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyFoodName(PatchFoodReq patchFoodReq) throws BaseException {
        try {
            int result = foodDao.modifyFoodName(patchFoodReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_FOODNAME);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public void deleteFoodName(DeleteFoodReq deleteFoodReq) throws BaseException {
        try {
            int result = foodDao.deleteFoodName(deleteFoodReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(DELETE_FAIL_ROWS_FOOD);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
