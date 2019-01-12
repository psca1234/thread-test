package com.psca.thread.threadgroup;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2019/1/12 10:33
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/12 10:33
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class ThreadGroupCreate {
    public static void main(String[] args) {
        ThreadGroup tg1 =new ThreadGroup("tg1");
        Thread t1 =new Thread(tg1,()->{
            Optional.of(Thread.currentThread().getName()+"\tsubordinate thread group names "+tg1.getName()).ifPresent(System.out :: println);
            Optional.of(Thread.currentThread().getName()+"\tsubordinate parent thread group names "+tg1.getParent().getName()).ifPresent(System.out :: println);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");

        t1.start();

        ThreadGroup tg2 = new ThreadGroup(tg1,"tg2");
        Thread t2 = new Thread(tg2,()->{
            Optional.of(Thread.currentThread().getName()+"\tsubordinate thread group names "+tg1.getName()).ifPresent(System.out :: println);
            Optional.of(Thread.currentThread().getName()+"\tsubordinate parent thread group names "+tg1.getParent().getName()).ifPresent(System.out :: println);
        },"t2");

        t2.start();

        Optional.of(Thread.currentThread().getName()+"\t subordinate thread group status is "+tg1.isDestroyed()).ifPresent(System.out :: println);
        Optional.of(Thread.currentThread().getName()+"\t subordinate thread group status is "+tg2.isDestroyed()).ifPresent(System.out :: println);
    }
}
