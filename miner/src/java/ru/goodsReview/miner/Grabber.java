/*
    Date: 10/26/11
    Time: 06:16
    Author: Alexander Marchuk
            aamarchuk@gmail.com
*/
package ru.goodsReview.miner;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.goodsReview.core.db.ControllerFactory;

import java.util.TimerTask;


public abstract class Grabber extends TimerTask {
    protected ControllerFactory controllerFactory;

    @Override
    public abstract void run();

    public void setControllerFactory(ControllerFactory controllerFactory){
        this.controllerFactory = controllerFactory;
    }
}
