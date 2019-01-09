import java.util.Optional;

/**
 * @Description: 获取线程内部异常信息
 * @Author: pansc
 * @CreateDate: 2019/1/9 22:07
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/9 22:07
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class TestCathException {
    private static final Integer A=10;
    private static final Integer B=0;
    public static void main(String[] args) {
        Thread t1 =new Thread(()->{
            try {
                Thread.sleep(5_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Integer result = A/B;
            Optional.of(Thread.currentThread().getName()+"\t>>"+result ).ifPresent(System.out :: println);
        });
        t1.start();

        t1.setUncaughtExceptionHandler((thread,e)->{
            Optional.of("当前线程名称为："+thread.getName()).ifPresent(System.out :: println);
            if(e instanceof RuntimeException){
                Optional.of(e.getMessage()).ifPresent(System.out :: println);
                new Thread(()->{
                    notifyMessage(thread,e);
                }).start();
            }
        });
    }

    private static void notifyMessage(Thread t,Throwable e){
        Optional.of(t.getName()+" throws a new exception,exception has message is"+e.getMessage()).ifPresent(System.out :: println);
        try {
            Thread.sleep(10_000L);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        Optional.of(t.getName()+" has released some resources ,include (file,data connection ........)").ifPresent(System.out :: println);
    }
}
