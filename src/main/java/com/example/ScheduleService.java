package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduleService {
    
    private static final Logger logger = LoggerFactory.getLogger("com.example");
    
    @Resource
    private JdbcTemplate jdbcTemplate;
    
    @Scheduled(cron = "${system.cron}")
    private void scheduledExecute() {
        logger.info("start execute...");
        List<Timestamp> dbtimes = jdbcTemplate.query("select now()", new RowMapper<Timestamp>() {
            @Override
            public Timestamp mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getTimestamp(1);
            }
        });
        logger.info("db time:{}", dbtimes);
        logger.info("end execute...");
    }
}