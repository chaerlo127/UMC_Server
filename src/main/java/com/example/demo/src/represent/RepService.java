package com.example.demo.src.represent;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.represent.model.DeleteRepReq;
import com.example.demo.src.represent.model.PatchRepReq;
import com.example.demo.src.represent.model.PostRepReq;
import com.example.demo.src.represent.model.PostRepRes;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class RepService {
    private final RepProvider repProvider;
    private final RepDao repDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RepService(RepProvider repProvider, RepDao repDao, JwtService jwtService){
        this.repProvider = repProvider;
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

    //회원가입
    public PostRepRes createRep(PostRepReq postRepReq) throws BaseException {
        if (repProvider.checkRepId(postRepReq.getRepId()) == 1) {
            throw new BaseException(POST_REPS_EXISTS_REPID);
        }
        String pwd;
        try {
            // 암호화: postUserReq에서 제공받은 비밀번호를 보안을 위해 암호화시켜 DB에 저장합니다.
            // ex) password123 -> dfhsjfkjdsnj4@!$!@chdsnjfwkenjfnsjfnjsd.fdsfaifsadjfjaf
            pwd = new AES128(Secret.REP_INFO_PASSWORD_KEY).encrypt(postRepReq.getPassword()); // 암호화코드
            postRepReq.setPassword(pwd);
        } catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            int repInx = repDao.createRep(postRepReq);
//            return new PostRepRes(repInx);
//            //jwt 발급.
            String jwt = jwtService.createRepJwt(repInx);
            return new PostRepRes(repInx, jwt);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //하나의 가게 정보 삭제
    public void deleteRep(DeleteRepReq deleteRepReq) throws BaseException {
        try {
            int result = repDao.deleteRep(deleteRepReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(DELETE_FAIL_ROWS_REP);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
