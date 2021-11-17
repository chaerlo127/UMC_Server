package com.example.demo.src.food;


import com.example.demo.src.food.Model.GetFoodRes;
import com.example.demo.src.food.Model.PostFoodReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class FoodDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


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
    public int checkFoodName(String FoodName) {
        String checkFoodNameQuery = "select exists(select foodName from Food where foodName = ?)";
        String checkFoodNameParmas = FoodName;
        return this.jdbcTemplate.queryForObject(checkFoodNameQuery,
                int.class,
                checkFoodNameParmas);
    }


    //foodName 생성
    public int createFood(PostFoodReq postFoodReq) {
        String createFoodQuery = "insert into Food (foodName) VALUES (?)"; // 실행될 동적 쿼리문
        Object[] createFoodParams = new Object[]{postFoodReq.getFoodName()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createFoodQuery, createFoodParams);


        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
    }

}
