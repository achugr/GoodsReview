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
    protected String config;

    public abstract void run();

    public abstract void setConfig(String config);

    public abstract void downloadPages(String path);

    public abstract void findPages(String path);

    public abstract void grabPages(String path);

    public abstract void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate);

}
