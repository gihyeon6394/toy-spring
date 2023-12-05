package com.example.toyspring.prepared;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConnectDB {


    public List<UserDto> userListByNameGroup(String group) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<UserDto> userDtoList = new ArrayList<>();

        try {

            // JDBC 드라이버 로딩
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB 연결 (가장 느린 부분, DB-WAS network delay)
            connection = DriverManager.getConnection("jdbc:log4jdbc:mysql://localhost:3306/toy_spring?autoReconnect=true", "root", "root");

            // PreparedStatement 생성
            statement = connection.prepareStatement("select id, name from user where name_group = ?");

            // 쿼리 파라미터 설정
            statement.setString(1, group);


            // 쿼리 실행
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                UserDto userDto = new UserDto(id, name);
                userDtoList.add(userDto);
            }

        } catch (SQLException e) {
            System.out.println("SQL Error : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패");
        } finally {
            // 자원 반납
            try {
                resultSet.close();
            } catch (Exception e) {
            }
            try {
                statement.close();
            } catch (Exception e) {
            }
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
        return userDtoList;
    }


}

