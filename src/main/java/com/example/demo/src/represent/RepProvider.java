package com.example.demo.src.represent;


import com.example.demo.config.BaseException;
import com.example.demo.src.represent.model.GetRepRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;


@Service
public class RepProvider {
    private final RepDao repDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RepProvider(RepDao repDao, JwtService jwtService){
        this.repDao = repDao;
        this.jwtService = jwtService;
    }



    public GetRepRes getrep(int repInx) throws BaseException{
        try {
            GetRepRes getRepRes = repDao.getRep(repInx);
            return getRepRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }




    public List<GetRepRes> getreps() throws BaseException{
        try {
            List<GetRepRes> getRepRes = repDao.getreps();
            return getRepRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetRepRes> getrepByRepId(String repId) throws BaseException{
        try {
            List<GetRepRes> getRepRes = repDao.getrepsByrepId(repId);
            return getRepRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public List<GetFoodRes> getrepByFoodInx(int foodInx) throws BaseException {
        try {
            List<GetFoodRes> getFoodRes = repDao.getrepByFoodInx(foodInx);
            return getFoodRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
