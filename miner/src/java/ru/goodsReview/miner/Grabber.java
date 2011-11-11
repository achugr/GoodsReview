/*
    Date: 10/26/11
    Time: 06:16
    Author: Alexander Marchuk
            aamarchuk@gmail.com
*/
package ru.goodsReview.miner;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import java.util.TimerTask;


public abstract class Grabber extends TimerTask {
    protected SimpleJdbcTemplate jdbcTemplate;

    @Override
    public abstract void run();

    public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
