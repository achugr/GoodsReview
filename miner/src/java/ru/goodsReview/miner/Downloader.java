package ru.goodsReview.miner;/*
*  Date: 11.12.11
*  Time: 19:45
*  Author:
*     Vanslov Evgeny
*     vans239@gmail.com
*/

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Downloader {
    private static class Task {
        public List<String> links;
        public String path;
    }

    private List<Task> tasks;
    private static Downloader instance = new Downloader();

    private Downloader() {
        tasks = new LinkedList<Task>();
    }

    public static Downloader getInstance() {
        return instance;
    }

    public void addLinks(List<String> links, String path) throws IOException{
        if(! new File(path).isDirectory())
            throw new IOException("Path directory doesn't exist");
        Task task = new Task();
        task.links.addAll(links);
        task.path = path;
        tasks.add(task);
    }

    public void run(){

    }
}
