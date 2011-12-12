package ru.goodsReview.core.utils;

/*
 *  Date: 11.12.11
 *  Time: 14:07
 *  Author:
 *     Vanslov Evgeny
 *     vans239@gmail.com
 */

import org.apache.log4j.Logger;
import ru.goodsReview.core.exception.DeleteException;
import java.io.File;

public class FileUtil {
    private static final Logger log = Logger.getLogger(FileUtil.class);

    private FileUtil(){};
    public static void cleanFolder(File f) throws DeleteException {
        if (!f.exists()) {
            log.info("Cleaning Folder " + f.getAbsolutePath() + " not exist");
            return;
        }
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                cleanFolder(files[i]);
            } else {
                if (!files[i].delete())
                    throw new DeleteException("Unavailable delete file");
            }
        }
        if (!f.delete())
            throw new DeleteException("Unavailable delete file");
        log.info(" Cleaning Folder " + f.getAbsolutePath() + " deleted successfully");
    }
}
