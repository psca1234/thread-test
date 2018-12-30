/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2018/12/30 21:08
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/30 21:08
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class BankCallNumberDemo {
    public static void main(String[] args) {
        ThreadSynchronizedDemo2 demo2 =new ThreadSynchronizedDemo2();
        Thread t1 =new Thread(demo2);
        Thread t2 =new Thread(demo2);
        Thread t3 =new Thread(demo2);
        t1.start();
        t2.start();
        t3.start();
    }
}
