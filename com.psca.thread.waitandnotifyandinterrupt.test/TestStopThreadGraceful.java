import java.util.Optional;

/**
 * @Description: 使用优雅的方式停止线程
 * @Author: pansc
 * @CreateDate: 2019/1/9 13:54
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/9 13:54
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class TestStopThreadGraceful {
    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(()->{
            while(true){
                if (Thread.interrupted()){
                    Optional.of("当前线程已经获取到打断标识，准备停止前线程").ifPresent(System.out :: println);
                    break;
                }
                Optional.of(Thread.currentThread().getName()+"线程正在工作!").ifPresent(System.out :: println);
            }
        });
        t1.start();
        Thread.sleep(100);
        t1.interrupt();

    }
}
