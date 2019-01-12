package com.psca.thread.cosumethreadpool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2019/1/12 12:43
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/12 12:43
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class SimpleCosumeThreadPool {
    private static int pool_size;
    private static final int DEFAULT_POOL_SIZE=10;
    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    private static final String THREAD_NAME_PREFIX="Simple_Thread_Pool-";
    private static final ThreadGroup GROUP=new ThreadGroup("Pool_Group");
    private static volatile int seq=0;
    private static final List<Thread> THREAD_QUEUE = new ArrayList<>();

    public SimpleCosumeThreadPool() {
        this(DEFAULT_POOL_SIZE);
    }

    public SimpleCosumeThreadPool(int poolSize){
        this.pool_size = poolSize;
        init();
    }

    private void init(){
        for(int i =0;i<pool_size;i++){
            createWorkTask();
        }
    }

    public void submit(Runnable runnable){
        synchronized(TASK_QUEUE){
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    private void createWorkTask(){
        WorkTask workTask = new WorkTask(GROUP,THREAD_NAME_PREFIX+(seq++));
        workTask.start();
        THREAD_QUEUE.add(workTask);
    }
    private enum TaskStatus{
        FREE,RUNNING,BLOCK,DEAD;
    }
    private static class WorkTask extends Thread{
        private volatile TaskStatus taskStatus = TaskStatus.FREE;

        public WorkTask(ThreadGroup group,String name){
            super(group,name);
        }
        public TaskStatus getThreadTaskStatus(){
            return this.taskStatus;
        }

        @Override
        public void run() {
            OUTER:
            while(taskStatus != TaskStatus.DEAD){
                Runnable runnable;
                synchronized (TASK_QUEUE){
                    while(TASK_QUEUE.isEmpty()){
                        try {
                            TASK_QUEUE.wait();
                            taskStatus = TaskStatus.BLOCK;
                        } catch (InterruptedException e) {
                            //e.printStackTrace();
                            break OUTER;
                        }

                    }
                    runnable = TASK_QUEUE.removeFirst();
                    if(runnable !=null){
                        taskStatus = TaskStatus.RUNNING;
                        runnable.run();
                        taskStatus = TaskStatus.FREE;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SimpleCosumeThreadPool threadPool = new SimpleCosumeThreadPool();
        for(int i=0;i<40;i++){
            threadPool.submit(()->{
                Optional.of("The runnable service by "+Thread.currentThread()+" begin .....").ifPresent(System.out :: println);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Optional.of("The runnable service by "+Thread.currentThread()+" end .....").ifPresent(System.out :: println);
            });
        }

        System.out.println("===================================");
    }
}
