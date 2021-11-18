package com.example.demo.src.represent;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.represent.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;


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



    // Login(password 검사)
    public PostRepLoginRes logIn(PostRepLoginReq postRepLoginReq) throws BaseException {
        Represent represent = repDao.getPwd(postRepLoginReq);
        String password;
        try {
            password = new AES128(Secret.REP_INFO_PASSWORD_KEY).decrypt(represent.getPassword()); // 암호화
            // 회원가입할 때 비밀번호가 암호화되어 저장되었기 떄문에 로그인을 할때도 암호화된 값끼리 비교를 해야합니다.
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if (postRepLoginReq.getPassword().equals(password)) { //비말번호가 일치한다면 userIdx를 가져온다.
            int repInx = repDao.getPwd(postRepLoginReq).getRepInx();
//            return new PostLoginRes(userIdx);
            String jwt = jwtService.createRepJwt(repInx);
            return new PostRepLoginRes(repInx,jwt);


        } else { // 비밀번호가 다르다면 에러메세지를 출력한다.
            throw new BaseException(FAILED_TO_LOGIN);
        }
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

    public int checkRepId(String repId) throws BaseException {
        try{
            return repDao.checkRepId(repId);
        }catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
