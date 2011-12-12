package ru.goodsReview.miner;/*
*  Date: 11.12.11
*  Time: 19:45
*  Author:
*     Vanslov Evgeny
*     vans239@gmail.com
*/

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Downloader implements Runnable {
    private static final Logger log = Logger.getLogger(Downloader.class);

    private static class Task {
        public Task(){
            links = new ArrayList<String>();
        }
        public List<String> links;
        public String path;
        public String encoding;
    }

    private int time = 500;
    private Queue<Task> tasks;
    private static Downloader instance = new Downloader();

    private Downloader() {
        tasks = new LinkedList<Task>();
    }

    public static Downloader getInstance() {
        return instance;
    }

    public void setSleepTime(int time) {
        this.time = time;
    }

    private void downloadOneLink(String urlStr, String filePath, String encoding) throws IOException {
        URL url = new URL(urlStr);
        URLConnection conn = url.openConnection();
        if (conn.getContentEncoding() != null) {
            encoding = conn.getContentEncoding();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
        FileWriter fw = new FileWriter(new File(filePath));
        String line = br.readLine();
        try {
            while (line != null) {
                fw.write(line);
                fw.write('\n');
                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }

    public void addLinks(List<String> links, String path, String encoding) throws IOException {
        if (!new File(path).isDirectory()) {
            throw new IOException("Path directory doesn't exist");
        }
        Task task = new Task();
        task.links.addAll(links);
        task.path = path;
        task.encoding = encoding;
        tasks.add(task);
    }

    public void run() {
        try {
            while (!tasks.isEmpty()) {
                if(!tasks.isEmpty()){
                    Task task = tasks.remove();
                    int i = 1;
                    for(String url : task.links){
                        try{
                            downloadOneLink( url, task.path + '/' + i + ".html", task.encoding);
                            ++i;
                            log.debug("Downloaded " + url);
                        } catch(IOException e){
                            log.error("Problem with loading url downloading", e);
                        }
                        Thread.sleep(time);
                    }
                }
                Thread.sleep(time);
            }
        } catch (InterruptedException e) {
            log.error("Somebody killed me", e);
        }
    }
}
