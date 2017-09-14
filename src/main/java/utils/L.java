package utils;

/**
 * Created by Yun on 2017/7/31 0031.
 */
public class L {

    public static void i(String msg){
        System.out.println("[info:] " + msg);
    }

    public static void i(Object object, String msg){
        System.out.println("[info:] " + object.getClass().getName() + " " + msg);
    }

    public static void i(String tag, String msg){
        System.out.println("[info:] " + tag + "-->" + msg);
    }

    public static void d(String tag, String msg){
        System.out.println("[debug:] " + tag + "-->" + msg);
    }

    public static void e(String tag, String msg){
        System.out.println("[error:] " + tag + "-->" + msg);
    }
}
