package com.example.demo.src.represent;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.represent.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // Rest API 또는 WebAPI를 개발하기 위한 어노테이션. @Controller + @ResponseBody 를 합친것.
// @Controller      [Presentation Layer에서 Contoller를 명시하기 위해 사용]
//  [Presentation Layer?] 클라이언트와 최초로 만나는 곳으로 데이터 입출력이 발생하는 곳
//  Web MVC 코드에 사용되는 어노테이션. @RequestMapping 어노테이션을 해당 어노테이션 밑에서만 사용할 수 있다.
// @ResponseBody    모든 method의 return object를 적절한 형태로 변환 후, HTTP Response Body에 담아 반환.
@RequestMapping("/app/represents")

public class RepController{
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final RepProvider repProvider;
    @Autowired
    private final RepService repService;
    @Autowired
    private final JwtService jwtService;

    public RepController(RepProvider repProvider, RepService repService, JwtService jwtService){
        this.repProvider = repProvider;
        this.repService = repService;
        this.jwtService = jwtService;
    }
    @ResponseBody
    @GetMapping("/{repInx}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetRepRes> getRep(@PathVariable("repInx") int repInx) {
        // @PathVariable RESTful(URL)에서 명시된 파라미터({})를 받는 어노테이션, 이 경우 userId값을 받아옴.
        //  null값 or 공백값이 들어가는 경우는 적용하지 말 것
        //  .(dot)이 포함된 경우, .을 포함한 그 뒤가 잘려서 들어감
        // Get Users
        try {
            GetRepRes getRepRes = repProvider.getrep(repInx);
            return new BaseResponse<>(getRepRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }




    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetRepRes>> getReps(@RequestParam(required = false) String repId){
        try {
            if (repId == null) {
                List<GetRepRes> getRepRes = repProvider.getreps();
                return new BaseResponse<>(getRepRes);
            }

            List<GetRepRes> getRepRes = repProvider.getrepByRepId(repId);
            return new BaseResponse<>(getRepRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @GetMapping("/food")
    public BaseResponse<List<GetFoodRes>> getFood(@RequestParam(required = false) int foodInx){
        try {

            List<GetFoodRes> getFoodRes = repProvider.getrepByFoodInx(foodInx);
            return new BaseResponse<>(getFoodRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //최소 주문금액 변경
    @ResponseBody
    @PatchMapping("/{repInx}")
    public BaseResponse<String> modifyMinPrice(@PathVariable("repInx") int repInx, @RequestBody Represent rep) {
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
            PatchRepReq patchRepReq = new PatchRepReq(repInx, rep.getMin_price());
            repService.modifyMinPrice(patchRepReq);

            String result = "회원정보가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // rep 회원가입
    @ResponseBody
    @PostMapping("/rep-sign-up")    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<PostRepRes> createRepresents(@RequestBody PostRepReq postRepReq) {
        if (postRepReq.getRepId() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_REPS_EMPTY_REPID);
        }
        try {
            PostRepRes postRepRes = repService.createRep(postRepReq);
            return new BaseResponse<>(postRepRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    //DELETE REP INFORMATION<TUPLE OF REPRESENT TABLE>
    @ResponseBody
    @DeleteMapping("/{repInx}")
    public BaseResponse<String> DeleteFood(@PathVariable("repInx") int repInx) {
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
            DeleteRepReq deleteRepReq = new DeleteRepReq(repInx);
            repService.deleteRep(deleteRepReq);

            String result = "RepID:" +repInx+ " 정보가 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
