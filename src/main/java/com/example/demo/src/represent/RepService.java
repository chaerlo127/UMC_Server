package com.example.demo.src.represent;

import com.example.demo.config.BaseException;
import com.example.demo.src.represent.model.PatchRepReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class RepService {
    private final RepDao repDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RepService(RepDao repDao, JwtService jwtService){
        this.repDao = repDao;
        this.jwtService = jwtService;
    }

    public void modifyMinPrice(PatchRepReq patchRepReq) throws BaseException {
        try {
            int result = repDao.modifyMinPrice(patchRepReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_REPPRICE);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
