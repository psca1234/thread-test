package com.psca.thread.cosumethreadpool;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.IntStream;

/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2019/1/14 14:46
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/14 14:46
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class SimpleThreadPoolDemo {

    private final int size;

    private final int queueSize;

    private final static int DEFAULT_SIZE=10;

    private final static int DEFAULT_TASK_QUEUE_SIZE=2000;

    private static volatile int seq = 0;

    private final static String THREAD_PROFIX="Simple_Thread_Pool-";

    private final static ThreadGroup GROUP = new ThreadGroup("Pool_Group");

    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    private final static ArrayList<WorkerTask> THREAD_QUEUE = new ArrayList<>();

    private final DiscardPolicy discardPolicy;

    public final static DiscardPolicy DEFAULT_DISCARD_POLICY=()->{
        throw new DiscardExcepton("Task has discard ...");
    };

    private boolean distory = false;

    public SimpleThreadPoolDemo(){
        this(DEFAULT_SIZE,DEFAULT_TASK_QUEUE_SIZE,DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPoolDemo(int size,int queueSize,DiscardPolicy discardPolicy) {
        this.size = size;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    private void init(){
        for (int i=0;i<size;i++){
            createWokerTask();
        }

    }

    public void submit(Runnable runnable){
        if(distory)
            throw new IllegalStateException("The thread pool already destory and not allow submit");
        synchronized (TASK_QUEUE){
            if(TASK_QUEUE.size() > queueSize)
                discardPolicy.discard();
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    private void createWokerTask(){
        WorkerTask task = new WorkerTask(GROUP,THREAD_PROFIX+(seq++));
        task.start();
        THREAD_QUEUE.add(task);
    }

    public void shutdown()throws InterruptedException{
        while(!TASK_QUEUE.isEmpty()){
            Thread.sleep(50);
        }
        int initVal = THREAD_QUEUE.size();
        while(initVal >0){
            for(WorkerTask workerTask :THREAD_QUEUE){
                workerTask.interrupt();
                workerTask.close();
                initVal--;
            }
        }
        distory = true;
        System.out.println("The thread pool is disposed ..");
    }

    public int getSize() {
        return size;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public boolean isDistory() {
        return distory;
    }

    private enum TaskState{
        FREE,RUNNING,BLOCK,DEAD
    }

    public static class DiscardExcepton extends RuntimeException{
        public DiscardExcepton(String message){
            super(message);
        }
    }

    public interface DiscardPolicy{
        void discard()throws DiscardExcepton;
    }

    private static class WorkerTask extends Thread{

        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup group,String name){
            super(group, name);
        }

        public TaskState getTaskState() {
            return taskState;
        }

        @Override
        public void run() {
            OUTER:
            while(this.taskState != TaskState.DEAD){
                Runnable runnable;
                synchronized (TASK_QUEUE){
                    while(TASK_QUEUE.isEmpty()){
                        try {
                            this.taskState = TaskState.BLOCK;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }

                    runnable =TASK_QUEUE.removeFirst();
                }
                if(runnable !=null){
                    this.taskState = TaskState.RUNNING;
                    runnable.run();
                    this.taskState = TaskState.FREE;
                }
            }
        }

        public void close(){
            this.taskState =TaskState.DEAD;
        }
    }

    public static void main(String[] args) {
        //SimpleThreadPoolDemo threadPoolDemo = new SimpleThreadPoolDemo(6,10,SimpleThreadPoolDemo.DEFAULT_DISCARD_POLICY);
        SimpleThreadPoolDemo threadPoolDemo =new SimpleThreadPoolDemo();
        for (int i=0;i<20;i++){
            threadPoolDemo.submit(()->{
                System.out.println("The runnable is sericed by\t"+Thread.currentThread()+"\tstart.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("The runnable is sericed by\t"+Thread.currentThread()+"\tfinished.");
            });
        }
        try {
            Thread.sleep(10000);
            threadPoolDemo.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("The thread pool status is \t"+threadPoolDemo.isDistory());
    }
}
