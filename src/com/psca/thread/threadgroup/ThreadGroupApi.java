package com.psca.thread.threadgroup;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2019/1/12 11:07
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/12 11:07
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class ThreadGroupApi {
    public static void main(String[] args) {
        ThreadGroup tg1= new ThreadGroup("tg1");
        Thread t1 = new Thread(tg1,()->{
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");
        t1.start();
        try{
            tg1.destroy();
        }catch (Exception e){
            if(e instanceof IllegalThreadStateException){
                StackTraceElement[] stacks = t1.getStackTrace();
                Arrays.asList(stacks).forEach(stackTraceElement -> {
                    Optional.of("-->"+stackTraceElement.getClassName()).ifPresent(System.out :: println);
                    Optional.of("出现异常的类名："+stackTraceElement.getClassName()+"\t出现异常的文件名称："+stackTraceElement.getFileName()).ifPresent(System.out :: println);
                    Optional.of("出现异常的方法名称："+stackTraceElement.getMethodName()+"\t出现异常的行号："+stackTraceElement.getLineNumber()).ifPresent(System.out :: println);
                });
            }
        }
        Optional.of(t1.getName()+"\t" + t1.getState()).ifPresent(System.out :: println);
        ThreadGroup mainThreadGroup = tg1.getParent();

        Thread [] threads = new Thread[mainThreadGroup.activeGroupCount()];

        mainThreadGroup.enumerate(threads,false);

        Optional.of("=================华丽的分割线=================").ifPresent(System.out :: println);

        Arrays.asList(threads).stream().forEach(t->{
            if(t != null){
                Optional.of(t.getName()+"\t"+t.getThreadGroup().getName()+"\t"+t.getThreadGroup().getMaxPriority()).ifPresent(System.out :: println);
            }
        });

        Thread [] threads1 =new Thread[mainThreadGroup.activeGroupCount()];
        mainThreadGroup.enumerate(threads1,true);

        Optional.of("=================华丽的分割线=================").ifPresent(System.out :: println);

        Arrays.asList(threads).stream().forEach(t->{
            if(t != null){
                Optional.of(t.getName()+"\t"+t.getThreadGroup().getName()+"\t"+t.getThreadGroup().getMaxPriority()).ifPresent(System.out :: println);
            }
        });
    }
}
