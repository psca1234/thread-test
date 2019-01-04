/**
 * @Description: 测试多线程情况下的生产者和消费者模式
 * @Author: pansc
 * @CreateDate: 2019/1/4 21:24
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/4 21:24
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class ProduceAndConsumeTest {

    private volatile boolean isProduced = false;

    private static int i = 0;

    private final static Object LOCK = new Object();

    public void produce() {
        synchronized (LOCK) {

            if (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                i++;
                System.out.println("P->" + i);
                LOCK.notify();
                isProduced = true;
            }
        }
    }

    public void consume() {
        synchronized (LOCK) {

            if (isProduced) {
                System.out.println("C->" + i);
                LOCK.notify();
                isProduced = false;
            } else {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProduceAndConsumeTest test = new ProduceAndConsumeTest();
        new Thread() {
            @Override
            public void run() {
                while (true)
                    test.produce();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                while (true)
                    test.consume();
            }
        }.start();
    }
}
