package com.psca.thread.cosumethreadpool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2019/1/14 14:46
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/14 14:46
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class SimpleThreadPoolDemo extends Thread{

    private int size;

    private final int queueSize;

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

    private volatile boolean distory = false;

    private int min;

    private int max;

    private int active;

    public SimpleThreadPoolDemo(){
        this(4,8,12,DEFAULT_TASK_QUEUE_SIZE,DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPoolDemo(int min,int active,int max,int queueSize,DiscardPolicy discardPolicy) {
        this.min=min;
        this.active = active;
        this.max=max;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    private void init(){
        for (int i=0;i<min;i++){
            createWokerTask();
        }

        this.size = min;
        this.start();
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

    @Override
    public void run() {
        while(!distory){
            System.out.printf("Pool#Min:%d,Active:%d,Max:%d,Current:%d,QueueSize:%d\n",
                    this.min, this.active, this.max, this.size, TASK_QUEUE.size());
            try {
                Thread.sleep(5_000);
                if(TASK_QUEUE.size()>active && size<active){
                    for(int i=size;i<active;i++){
                        createWokerTask();
                    }
                    System.out.println("The pool incremented to active.");
                    size = active;
                } else if (TASK_QUEUE.size() > max && size < max) {
                    for (int i = size; i < max; i++) {
                        createWokerTask();
                    }
                    System.out.println("The pool incremented to max.");
                    size = max;
                }

                synchronized (THREAD_QUEUE) {
                    if (TASK_QUEUE.isEmpty() && size > active) {
                        System.out.println("=========Reduce========");
                        int releaseSize = size - active;
                        for (Iterator<WorkerTask> it = THREAD_QUEUE.iterator(); it.hasNext(); ) {
                            if (releaseSize <= 0)
                                break;

                            WorkerTask task = it.next();
                            task.close();
                            task.interrupt();
                            it.remove();
                            releaseSize--;
                        }
                        size = active;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        System.out.println(GROUP.activeCount());

        this.distory = true;
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

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getActive() {
        return active;
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

    public static void main(String[] args) throws InterruptedException{
        SimpleThreadPool threadPool = new SimpleThreadPool();
        for (int i = 0; i < 40; i++) {
            threadPool.submit(() -> {
                System.out.println("The runnable  be serviced by " + Thread.currentThread() + " start.");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("The runnable be serviced by " + Thread.currentThread() + " finished.");
            });
        }

        Thread.sleep(10000);
        threadPool.shutdown();
    }
}
