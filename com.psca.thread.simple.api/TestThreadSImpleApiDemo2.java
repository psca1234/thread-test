import java.util.Optional;

/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2018/12/30 11:15
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/30 11:15
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class TestThreadSImpleApiDemo2 {
    public static void main(String [] args){
        Thread t1 =new Thread(()->{
            Optional.of("hello").ifPresent(System.out :: println);
        },"t1");
        Thread t2 =new Thread(()->{
            Optional.of("world").ifPresent(System.out :: println);
        },"t2");
        Thread t3 =new Thread(()->{
            Optional.of("My name is panshichao").ifPresent(System.out :: println);
        },"t3");
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.NORM_PRIORITY);
        t3.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        t2.start();
        t3.start();
        /**
         * My name is panshichao
         * hello
         * world
         * 运行结果说明，人工设置线程的优先级不能真实的控制线程的执行顺序
         */
    }
}
