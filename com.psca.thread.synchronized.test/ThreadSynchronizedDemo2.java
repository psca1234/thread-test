/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2018/12/30 21:01
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/30 21:01
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class ThreadSynchronizedDemo2 implements Runnable{
    private static int index =0 ;
    private static final int MAX=500;
    private final Object MONITOR=new Object();
    @Override
    public void run() {
        while(true){
            synchronized (MONITOR){
                if(index > MAX){
                    break;
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"叫号器，呼叫的当前号码为:"+(index++));
            }
        }

    }
}
