package ru.goodsReview.core.utils;

/**
 * Artemij Chugreev
 * Date: 30.03.12
 * Time: 23:43
 * email: artemij.chugreev@gmail.com
 * skype: achugr
 */
public class OSValidator {
    
    public static boolean isWindows(){
        String os=System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }

    public static boolean isUnix(){
        String os=System.getProperty("os.name").toLowerCase();
        return os.contains("nix") || os.contains("nux");
    }

    public static boolean isMac() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("mac");
    }
}
