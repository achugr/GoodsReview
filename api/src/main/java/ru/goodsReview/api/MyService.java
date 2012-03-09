package ru.goodsReview.api;

import com.sun.jersey.api.core.PackagesResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Artemij Chugreev
 * Date: 07.03.12
 * Time: 2:10
 * email: achugr@yandex-team.ru
 * skype: achugr
 */

@ApplicationPath("resources")
public class MyService extends PackagesResourceConfig {
    public MyService(){
        super("com.sun.jersey.config.property.packages", "ru.goodsReview.api.resources");
    }
}
