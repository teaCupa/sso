package org.yh.ssoclient.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: yh
 * @Date: 2020/9/20
 * @Description:
 */


@Component
public class Task {

    /**
     * 每天凌晨2点30分0秒执行
     */
    @Scheduled(cron = "0 30 2 * * *")
    public void task2() {
        System.out.println("[ 当前时间" + LocalDateTime.now() + " ]");
    }
}
