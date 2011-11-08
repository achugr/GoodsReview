/*
    Date: 10/26/11
    Time: 06:16
    Author: Alexander Marchuk
            aamarchuk@gmail.com
*/
package ru.goodsReview.miner;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;


public abstract class Grabber extends TimerTask {
	protected SimpleJdbcTemplate jdbcTemplate;
	protected String config;

	public abstract void run();

	public abstract void setConfig(String config);

	public abstract void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate);

    public static Date getDate(String dateStr) {
		if (dateStr == null)
			return null;
		dateStr = dateStr.replaceAll("( )+$", "");
		if (dateStr.contains("сегодня")) {
			return new Date();
		}
		GregorianCalendar calendar = new GregorianCalendar();
		if (dateStr.contains("вчера")) {
			calendar.add(Calendar.DATE, -1);
			return calendar.getTime();
		}

		String[] russianMonth =
				{
						"января",
						"февраля",
						"марта",
						"апреля",
						"мая",
						"июня",
						"июля",
						"августа",
						"сентября",
						"октября",
						"ноября",
						"декабря"
				};
		Locale local = new Locale("ru", "RU");
		DateFormatSymbols russSymbol = new DateFormatSymbols(local);
		russSymbol.setMonths(russianMonth);
		SimpleDateFormat sdf = new SimpleDateFormat("d MMMMM HH:mm ", russSymbol);

		Date date = null;
		try {
			date = sdf.parse(dateStr + " " + calendar.get(Calendar.YEAR));
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return date;
	}

}