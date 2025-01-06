package com.nhnacademy.exam.hotel.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://133.186.241.167:18080/nhn_exam_43");
        dataSource.setUsername("nhn_exam_43");
        dataSource.setPassword("sA4fxbhw!");

        dataSource.setInitialSize(100);     // 초기 커넥션 개수
        dataSource.setMaxTotal(100);        // 최대 커넥션 개수
        dataSource.setMinIdle(100);         // 최소 유휴 커넥션 개수
        dataSource.setMaxIdle(100);         // 최대 유휴 커넥션 개수

        // Connection 유효성 검사를 위한 설정
        dataSource.setTestOnBorrow(true);           // 커넥션 획득 전 테스트
        dataSource.setValidationQuery("SELECT 1");  // 커넥션 유효성 검사 쿼리

        return dataSource;
    }

}
