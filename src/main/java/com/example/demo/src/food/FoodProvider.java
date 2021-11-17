package com.example.demo.src.food;

import com.example.demo.config.BaseException;
import com.example.demo.src.food.Model.GetFoodRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class FoodProvider {

    private final FoodDao foodDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FoodProvider(FoodDao foodDao, JwtService jwtService){
        this.foodDao = foodDao;
        this.jwtService = jwtService;
    }

    public GetFoodRes getFood(int foodInx) throws BaseException {
        try{
            GetFoodRes getFoodRes = foodDao.getFood(foodInx);
            return getFoodRes;
        }catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
