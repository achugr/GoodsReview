package ru.goodsreview.miner.core;

/**
 * User: daddy-bear
 * Date: 26.05.12
 * Time: 9:58
 */
public class DownloadException extends Exception {
    public DownloadException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DownloadException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DownloadException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DownloadException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
