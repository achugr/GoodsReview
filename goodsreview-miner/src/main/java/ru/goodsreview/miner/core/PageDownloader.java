package ru.goodsreview.miner.core;

/**
 * User: daddy-bear
 * Date: 25.05.12
 * Time: 23:22
 *
 *
 */
public interface PageDownloader {

    Page download(final String url) throws DownloadException;

}
