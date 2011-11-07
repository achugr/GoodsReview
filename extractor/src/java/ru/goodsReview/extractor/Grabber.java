/*
    Date: 10/26/11
    Time: 06:16
    Author: Alexander Marchuk
            aamarchuk@gmail.com
*/
package ru.goodsReview.extractor;

import java.util.TimerTask;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;


public abstract class Grabber extends TimerTask {
	protected SimpleJdbcTemplate jdbcTemplate;
	protected String config;

	public abstract void run();

	public abstract void setConfig(String config);

	public abstract void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate);

}