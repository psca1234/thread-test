import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2019/1/9 18:21
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/9 18:21
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class TestCosumerLock {
    public static void main(String[] args) {
        BooleanLock booleanLock = new BooleanLock();
        Stream.of("T1","T2","T3","T4")
                .forEach(name ->{
                    new Thread(()->{
                        try {
//                            booleanLock.lock();
                            booleanLock.lock(10_000);
                            Optional.of(Thread.currentThread().getName()+" has geted Monitor..........")
                                    .ifPresent(System.out :: println);
                            work();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Lock.TimeoutExcetion timeoutExcetion) {
                            Optional.of(timeoutExcetion.getMessage()).ifPresent(System.out :: println);
                        } finally {
                            booleanLock.unlock();
                        }
                    }).start();
                });
    }

    private static void work()throws InterruptedException{
        Optional.of(Thread.currentThread().getName()+" is working ................")
                .ifPresent(System.out :: println);
        Thread.sleep(10_000);
    }
}
