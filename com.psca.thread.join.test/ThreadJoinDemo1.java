/**
 * @Description: thread的join方法练习1
 * @Author: pansc
 * @CreateDate: 2018/12/30 13:39
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/30 13:39
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class ThreadJoinDemo1 {
    public static void main(String [] args)throws InterruptedException{
        Thread t1 =new Thread(()->{
            String threadName = Thread.currentThread().getName();
            for(int i=0;i<1000;i++){
                System.out.printf("%s is running with index value is %s",threadName,i+"\n");
            }
        });

        t1.start();
        t1.join();
        String threadName = Thread.currentThread().getName();
        for(int i =1;i<=1000;i++){
            System.out.printf("%s is running with index value is %s",threadName,i+"\n");
        }
        System.out.println("all the threads is running end ...");
    }
}
