/**
 * @Description: 通过jconsole、jstack、javap-c（汇编命令）了解synchronized工作原理
 * @Author: pansc
 * @CreateDate: 2018/12/30 20:21
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/30 20:21
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class ThreadSychronizedDemo {
    private static final Object OBJ =new Object();
    public static void main(String[] args) {
        Runnable runnable =()->{
            synchronized (OBJ){
                try {
                    Thread.sleep(200_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);
        t1.start();
        t2.start();
        t3.start();
    }
}
