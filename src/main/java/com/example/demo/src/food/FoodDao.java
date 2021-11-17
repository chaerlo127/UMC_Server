package com.example.demo.src.food;


import com.example.demo.src.food.Model.GetFoodRes;
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

}
