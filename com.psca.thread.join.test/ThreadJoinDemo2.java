import java.util.stream.IntStream;

/**
 * @Description: 多个自定义线程调用join方法
 * @Author: pansc
 * @CreateDate: 2018/12/30 14:00
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/30 14:00
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class ThreadJoinDemo2 {
    public static void main(String [] args)throws InterruptedException{
        Thread t1 =new Thread(()->{
            String threadName = Thread.currentThread().getName();
            IntStream.range(1,100).forEach(i->System.out.printf("%s is running with index value is %s",threadName,i+"\n"));
        });
        Thread t2 =new Thread(()->{
            String threadName = Thread.currentThread().getName();
            IntStream.range(1,100).forEach(i->System.out.printf("%s is running with index value is %s",threadName,i+"\n"));
        });
        Thread t3 =new Thread(()->{
            String threadName = Thread.currentThread().getName();
            IntStream.range(1,100).forEach(i->System.out.printf("%s is running with index value is %s",threadName,i+"\n"));
        });
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        String mainThreadName = Thread.currentThread().getName();
        IntStream.range(1,100).forEach(i -> System.out.printf("%s is running with index value is %s",mainThreadName,i+"\n"));
    }
}
