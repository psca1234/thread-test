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

    private final static int DEFAULT_SIZE=10;

    private static volatile int seq = 0;

    private final static String THREAD_PROFIX="Simple_Thread_Pool-";

    private final static ThreadGroup GROUP = new ThreadGroup("Pool_Group");

    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    private final static ArrayList<WorkerTask> THREAD_QUEUE = new ArrayList<>();

    public SimpleThreadPoolDemo(){
        this(DEFAULT_SIZE);
    }

    public SimpleThreadPoolDemo(int size) {
        this.size = size;
        init();
    }

    private void init(){
        for (int i=0;i<size;i++){
            createWokerTask();
        }

    }

    public void submit(Runnable runnable){
        synchronized (TASK_QUEUE){
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    private void createWokerTask(){
        WorkerTask task = new WorkerTask(GROUP,THREAD_PROFIX+(seq++));
        task.start();
        THREAD_QUEUE.add(task);
    }

    private enum TaskState{
        FREE,RUNNING,BLOCK,DEAD
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
        SimpleThreadPoolDemo threadPoolDemo = new SimpleThreadPoolDemo();
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
    }
}
