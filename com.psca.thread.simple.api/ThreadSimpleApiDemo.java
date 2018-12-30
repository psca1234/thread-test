import java.util.Optional;

/**
 * @Description: 练习getName、getId、getPriority方法
 * @Author: pansc
 * @CreateDate: 2018/12/30 11:01
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/30 11:01
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class ThreadSimpleApiDemo {
    public static void main(String [] args){
        Thread t1 =new Thread(()->{
            Optional.of("Hello").ifPresent(System.out::println);
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");
        t1.start();
        Optional.of(t1.getName()).ifPresent(System.out::println);
        Optional.of(t1.getId()).ifPresent(System.out::println);
        Optional.of(t1.getPriority()).ifPresent(System.out::println);
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
