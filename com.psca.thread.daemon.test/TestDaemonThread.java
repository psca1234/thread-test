/**
 * @Description: 测试守护线程API
 * @Author: pansc
 * @CreateDate: 2018/12/30 10:12
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/30 10:12
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class TestDaemonThread {
    public static void main(String [] args){
        Thread t1 = new Thread(){
            @Override
            public void run() {
                Thread t2 =new Thread(){
                    @Override
                    public void run() {
                        System.out.println("Do  some thing for health check");
                    }
                };
                t2.setDaemon(true);
                t2.start();
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程执行结束");
    }
}
