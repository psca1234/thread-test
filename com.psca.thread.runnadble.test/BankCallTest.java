/**
 * @Description: z模拟银行叫号机
 * @Author: pansc
 * @CreateDate: 2018/12/29 20:14
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/29 20:14
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class BankCallTest {
    public static void main(String [] args){
        final ThreadCreatedBuRunnable threadCreatedBuRunnable =new ThreadCreatedBuRunnable();
        Thread thread1 = new Thread(threadCreatedBuRunnable,"一号柜台");
        Thread thread2 = new Thread(threadCreatedBuRunnable,"二号柜台");
        Thread thread3 = new Thread(threadCreatedBuRunnable,"三号柜台");
        Thread thread4 = new Thread(threadCreatedBuRunnable,"四号柜台");
        Thread thread5 = new Thread(threadCreatedBuRunnable,"五号柜台");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}
