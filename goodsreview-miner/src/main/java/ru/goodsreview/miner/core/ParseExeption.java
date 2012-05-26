package ru.goodsreview.miner.core;

/**
 * User: daddy-bear
 * Date: 26.05.12
 * Time: 10:08
 */
public class ParseExeption extends Exception {
    public ParseExeption() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ParseExeption(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ParseExeption(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ParseExeption(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
