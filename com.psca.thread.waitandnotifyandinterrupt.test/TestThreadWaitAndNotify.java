import java.util.*;

/**
 * @Description: 控制可执行的线程的数量
 * @Author: pansc
 * @CreateDate: 2019/1/9 15:40
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/9 15:40
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class TestThreadWaitAndNotify {
    private static LinkedList<Control> Controls =new LinkedList<>();
    private static final Integer MAX_SIZE=5;
    public static void main(String[] args) {
        List<Thread> workers = new ArrayList<>();
         Arrays.asList("M1","M2","M3","M4","M5","M6","M7","M8","M9","M10").stream()
                .map(TestThreadWaitAndNotify::craeteThread)
                .forEach(t->{
                    t.start();
                    workers.add(t);
        });

        workers.stream().forEach(t->{
             try {
                 t.join();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         });

        Optional.of("All Threads works end ..........").ifPresent(System.out :: println);
    }

    private static Thread craeteThread(String name){
        return new Thread(()->{
            synchronized (Controls){
                Optional.of("The Thread names "+Thread.currentThread().getName()+ "is working begin .......").ifPresent(System.out :: println);
                while(Controls.size() >MAX_SIZE){
                    try {
                        Controls.wait();
                        Controls.addLast(new Control());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Optional.of("The Thread names " + Thread.currentThread().getName()+ "is working...............").ifPresent(System.out :: println);
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized(Controls){
                Optional.of("The Thread names "+Thread.currentThread().getName()+ "is worked end ..........").ifPresent(System.out :: println);
                Controls.removeFirst();
                Controls.notifyAll();
            }
        },name);
    }

    private static class Control{}
}
