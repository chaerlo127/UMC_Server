package com.example.demo.src.represent;

import com.example.demo.src.represent.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class RepDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public GetRepRes getRep(int repInx) {
        String getRepQuery = "select repInx,name,RepId,password,min_price,address,close_time," +
                "phone,foodInx from Represent where repInx = ?";
        int getRepParams = repInx;
        return this.jdbcTemplate.queryForObject(getRepQuery,
                (rs, rowNum) -> new GetRepRes(
                        rs.getInt("repInx"),
                        rs.getString("name"),
                        rs.getString("RepId"),
                        rs.getString("password"),
                        rs.getInt("min_price"),
                        rs.getString("address"),
                        rs.getString("close_time"),
                        rs.getString("phone"),
                        rs.getInt("foodInx")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getRepParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }




    public List<GetRepRes> getreps() {
        String getRepQuery = "select repInx,name,RepId,password,min_price,address,close_time,phone,foodInx from Represent"; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getRepQuery,
                (rs, rowNum) -> new GetRepRes(
                        rs.getInt("repInx"),
                        rs.getString("name"),
                        rs.getString("RepId"),
                        rs.getString("password"),
                        rs.getInt("min_price"),
                        rs.getString("address"),
                        rs.getString("close_time"),
                        rs.getString("phone"),
                        rs.getInt("foodInx")) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        );
    }

    public List<GetRepRes> getrepsByrepId(String repId) {
        String getRepByrepIdQuery = "select repInx,name,RepId,password,min_price," +
                "address,close_time,phone,foodInx from Represent where repId =?"; // 해당 이메일을 만족하는 유저를 조회하는 쿼리문
        String getRepByrepIdParams = repId;
        return this.jdbcTemplate.query(getRepByrepIdQuery,
                (rs, rowNum) -> new GetRepRes(
                        rs.getInt("repInx"),
                        rs.getString("name"),
                        rs.getString("RepId"),
                        rs.getString("password"),
                        rs.getInt("min_price"),
                        rs.getString("address"),
                        rs.getString("close_time"),
                        rs.getString("phone"),
                        rs.getInt("foodInx")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getRepByrepIdParams);
    }

    public List<GetRepRes> getrepsPage(String pageSQL, String sizeSQL) {
        String getRepByrepIdQuery = "select repInx,name,RepId,password,min_price," +
                "address,close_time,phone,foodInx from Represent limit " + pageSQL+","+sizeSQL; // 해당 이메일을 만족하는 유저를 조회하는 쿼리문

        return this.jdbcTemplate.query(getRepByrepIdQuery,
                (rs, rowNum) -> new GetRepRes(
                        rs.getInt("repInx"),
                        rs.getString("name"),
                        rs.getString("RepId"),
                        rs.getString("password"),
                        rs.getInt("min_price"),
                        rs.getString("address"),
                        rs.getString("close_time"),
                        rs.getString("phone"),
                        rs.getInt("foodInx"))
        );
    }

    public List<GetFoodRes> getrepByFoodInx(int foodInx) {
        String getRepByFoodInx = "select repInx, name, foodInx " +
                "from Represent " +
                "where foodInx=?"; // 해당 이메일을 만족하는 유저를 조회하는 쿼리문
        int getRepByFoodParams = foodInx;
        return this.jdbcTemplate.query(getRepByFoodInx,
                (rs, rowNum) -> new GetFoodRes(
                        rs.getInt("repInx"),
                        rs.getString("name"),
                        rs.getInt("foodInx")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getRepByFoodParams);
    }


    public int modifyMinPrice(PatchRepReq patchRepReq) {
        String modifyMinPriceQuery = "update Represent set min_price = ? where repInx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyMinPriceParams = new Object[]{patchRepReq.getMin_price(), patchRepReq.getRepInx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyMinPriceQuery, modifyMinPriceParams);

    }

    // 로그인: 해당 email에 해당되는 user의 암호화된 비밀번호 값을 가져온다.
    public Represent getPwd(PostRepLoginReq postRepLoginReq) {
        String getPwdQuery = "select repInx,name,repId,password,min_price," +
                "address,close_time,phone,closed_date,foodInx from Represent where repId = ?"; // 해당 email을 만족하는 User의 정보들을 조회한다.
        String getPwdParams = postRepLoginReq.getRepId(); // 주입될 repId값을 클라이언트의 요청에서 주어진 정보를 통해 가져온다.

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new Represent(
                        rs.getInt("repInx"),
                        rs.getString("name"),
                        rs.getString("RepId"),
                        rs.getString("password"),
                        rs.getInt("min_price"),
                        rs.getString("address"),
                        rs.getString("close_time"),
                        rs.getString("phone"),
                        rs.getString("closed_date"),
                        rs.getInt("foodInx")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getPwdParams
        ); // 한 개의 회원정보를 얻기 위한 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    //repId 중복 확인
    public int checkRepId(String repId) {
        String checkRepNameQuery = "select exists(select repId from Represent where repId = ?)";
        String checkRepNameParams = repId;
        return this.jdbcTemplate.queryForObject(checkRepNameQuery,
                int.class,
                checkRepNameParams);
    }

    //represent 회원가입
    public int createRep(PostRepReq postRepReq) {
        String createRepQuery = "insert into Represent (name, repId, password, min_price, address, " +
                "close_time, foodInx, closed_date, phone) VALUES (?,?,?,?,?,?,?,?,?)"; // 실행될 동적 쿼리문
        Object[] createRepParams = new Object[]{postRepReq.getName(), postRepReq.getRepId(), postRepReq.getPassword(),
        postRepReq.getMin_price(), postRepReq.getAddress(), postRepReq.getClose_time(), postRepReq.getFoodInx(),
        postRepReq.getClosed_date(), postRepReq.getPhone()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createRepQuery, createRepParams);


        String lastInsertIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    //REP 삭제<TUPLE>
    public int deleteRep(DeleteRepReq deleteRepReq) {
        String deleteFoodNameQuery = "delete from Represent where RepInx = ?"; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] deleteFoodNameParams = new Object[]{deleteRepReq.getRepInx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(deleteFoodNameQuery, deleteFoodNameParams);
    }
}