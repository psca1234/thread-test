import java.util.Arrays;

/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2018/12/30 0:36
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/30 0:36
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class TestThreadGoupDemo {
    public static void main(String [] args){
        Thread t1 =new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        Thread parentThread = Thread.currentThread();
        ThreadGroup currentThreadGroup = parentThread.getThreadGroup();
        Integer activeThreadCount = currentThreadGroup.activeCount();
        System.out.println("当前活跃的线程数为："+activeThreadCount);
        /*
        *Thread[Monitor Ctrl-Break,5,main]
        * 之所以程序打印结果与我们预估的结果不同，是因为有守护线程的存在
        *
         */
        Thread [] activeThreads =new Thread[activeThreadCount];
        currentThreadGroup.enumerate(activeThreads);
        Arrays.asList(activeThreads).forEach(System.out :: println);
    }
}
