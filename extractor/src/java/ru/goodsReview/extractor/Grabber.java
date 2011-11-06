/*
    Date: 10/26/11
    Time: 06:16
    Author: Alexander Marchuk
            aamarchuk@gmail.com
*/
package ru.goodsReview.extractor;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

public abstract class Grabber {
    protected SimpleJdbcTemplate jdbcTemplate;
    protected String config;

    public abstract void grab();

    public abstract void setConfig(String config);

    public abstract void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate);
}