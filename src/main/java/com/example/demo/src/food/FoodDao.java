package com.example.demo.src.food;


import com.example.demo.src.food.Model.DeleteFoodReq;
import com.example.demo.src.food.Model.GetFoodRes;
import com.example.demo.src.food.Model.PatchFoodReq;
import com.example.demo.src.food.Model.PostFoodReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FoodDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Transactional(readOnly = true)
    public GetFoodRes getFood(int foodInx) {
        String getFoodQuery = "select foodInx, foodName from Food where foodInx=?";
        int foodRepParams = foodInx;
        return this.jdbcTemplate.queryForObject(getFoodQuery,
                (rs, rowNum) -> new GetFoodRes(
                        rs.getInt("foodInx"),
                        rs.getString("foodName")
                ), foodRepParams);
    }

    //foodName이 중복되어 있는지 확인하기 위한 functions
    //성공
    @Transactional(readOnly = true)
    public int checkFoodName(String FoodName) {
        String checkFoodNameQuery = "select exists(select foodName from Food where foodName = ?)";
        String checkFoodNameParmas = FoodName;
        return this.jdbcTemplate.queryForObject(checkFoodNameQuery,
                int.class,
                checkFoodNameParmas);
    }


    //foodName 생성
    @Transactional
    public int createFood(PostFoodReq postFoodReq) {
        String createFoodQuery = "insert into Food (foodName) VALUES (?)"; // 실행될 동적 쿼리문
        Object[] createFoodParams = new Object[]{postFoodReq.getFoodName()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createFoodQuery, createFoodParams);


        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
    }


    @Transactional
    public int modifyFoodName(PatchFoodReq patchFoodReq) {
        String modifyFoodNameQuery = "update Food set foodName = ? where foodInx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyFoodNameParams = new Object[]{patchFoodReq.getFoodName(), patchFoodReq.getFoodInx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyFoodNameQuery, modifyFoodNameParams);

    }

    //foods 전부 조회

    @Transactional(readOnly = true)
    public List<GetFoodRes> getFoods(String pageSQL, String sizeSQL) {
        String getFoodsQuery = "select * from Food limit "+ pageSQL +","+ sizeSQL; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getFoodsQuery,
                (rs, rowNum) -> new GetFoodRes(
                        rs.getInt("foodInx"),
                        rs.getString("foodName")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        ); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)
    }


    @Transactional
    public int deleteFoodName(DeleteFoodReq deleteFoodReq) {
        String deleteFoodNameQuery = "delete from Food where foodInx = ?"; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] deleteFoodNameParams = new Object[]{deleteFoodReq.getFoodInx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(deleteFoodNameQuery, deleteFoodNameParams);
    }

    //마지막의 foodInx 번호 불러옴
    @Transactional
    public int getLastfoodInx() {
        String getLastfoodInxQuery = "select count(*) from Food";
        return this.jdbcTemplate.queryForObject(getLastfoodInxQuery, int.class);
    }
}
