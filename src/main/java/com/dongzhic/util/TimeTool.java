package com.dongzhic.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author dongzhic
 * @Date 2020-10-16 14:01
 */
public class TimeTool {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    public interface Task {
        void execute();
    }

    public static void check (String title, Task task) {
        if (task == null) {
            return;
        }
        title = (title == null) ? "" : ("【" + title + "】");
        System.out.println(title);
        System.out.println("开始：" + sdf.format(new Date()));
        long begin = System.currentTimeMillis();
        task.execute();
        long end = System.currentTimeMillis();
        System.out.println("结束：" + sdf.format(new Date()));
        double delta = (end - begin) / 1000.0;
        System.out.println("耗时：" + delta + "秒");
        System.out.println("--------------------------------------------------");

    }
}
