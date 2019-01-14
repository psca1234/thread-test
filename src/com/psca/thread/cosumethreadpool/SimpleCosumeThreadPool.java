package com.psca.thread.cosumethreadpool;

import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2019/1/12 19:24
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/12 19:24
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class SimpleCosumeThreadPool {

    private static int pool_thread_size;
    private static int thread_run_task_size;
    private final static int DEFAULT_POOL_THREAD_SIZE=10;
    private final static int DEFAULT_THREAD_RUN_TASK_SIZE=200;
    private final static LinkedList<Runnable> WORK_TASKS=new LinkedList<>();
    private final static ArrayList<WorkTask> THREAD_QUEUE=new ArrayList<>();
    private final static String THREAD_POOL_PREFIX="Simple_Thread_Pool-";
    private final static ThreadGroup GROUP = new ThreadGroup("Pool_Thread");
    private static volatile int seq=0;

    public SimpleCosumeThreadPool(){
        this(DEFAULT_POOL_THREAD_SIZE,DEFAULT_THREAD_RUN_TASK_SIZE);
    }

    public SimpleCosumeThreadPool(int poolThreadSize,int threadRunTaskSize){
        this.pool_thread_size=poolThreadSize;
        this.thread_run_task_size=threadRunTaskSize;
        init();
    }

    private void init(){
        for (int i=0;i<pool_thread_size;i++){
            createWorkTask();
        }
    }
    private void createWorkTask(){
        WorkTask task = new WorkTask(GROUP,THREAD_POOL_PREFIX+(seq++));
        task.start();
        THREAD_QUEUE.add(task);
    }

    private enum TaskStatus{
        FREE,RUNNING,BLOCK,DEAD;
    }
    private static class WorkTask extends Thread{
        private volatile TaskStatus taskStatus = TaskStatus.FREE;
        public WorkTask(ThreadGroup group,String name){
            super(group,name);
        }
        public TaskStatus getTaskStatus(){
            return this.taskStatus;
        }
        @Override
        public void run() {
            OUTER:
            while(this.taskStatus != TaskStatus.DEAD){
                synchronized (WORK_TASKS){
                    Runnable runnable;
                    while(WORK_TASKS.isEmpty()){
                        try {
                            this.taskStatus = TaskStatus.BLOCK;
                            WORK_TASKS.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }
                    runnable = WORK_TASKS.removeFirst();
                    if(runnable != null){
                        this.taskStatus = TaskStatus.RUNNING;
                        Optional.of(Thread.currentThread()+"\tbegins working.....").ifPresent(System.out :: println);
                        runnable.run();
                        Optional.of(Thread.currentThread()+"\tend working.....").ifPresent(System.out :: println);
                        this.taskStatus = TaskStatus.FREE;
                    }
                }
            }
        }
    }

    public void submit(Runnable runnable){
        synchronized (WORK_TASKS){
            WORK_TASKS.addLast(runnable);
            WORK_TASKS.notifyAll();
        }
    }

    public static void main(String[] args) {
        SimpleCosumeThreadPool threadPool =new SimpleCosumeThreadPool();
        int count=0;
        for (int i=0;i<40;i++){

            threadPool.submit(()->{
                Optional.of("the thread names\t"+Thread.currentThread().getName()+"\t begins working..........").ifPresent(System.out :: println);
                Optional.of("the thread names\t"+Thread.currentThread().getName()+"\t working end..........").ifPresent(System.out :: println);
            });
            count++;
        }
        System.out.println("======================"+count);
    }
}
