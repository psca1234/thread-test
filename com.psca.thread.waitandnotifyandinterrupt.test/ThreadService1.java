import java.util.Optional;

/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2019/1/9 14:25
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/9 14:25
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class ThreadService1 {

    private Thread excuteThread;
    private boolean finished = false;
    public void excute(Runnable task){
        excuteThread = new Thread(){
            @Override
            public void run() {
                Thread runner =new Thread(task);
                runner.setDaemon(true);
                runner.start();
                try {
                    runner.join();
                    finished = true;
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }
        };
        excuteThread.start();
    }

    public void shutdown(long millons){
        long startMillons = System.currentTimeMillis();
        while(!finished){
            if((System.currentTimeMillis()-startMillons) >millons){
                Optional.of("任务超时，需要结束他！").ifPresent(System.out :: println);
                excuteThread.interrupt();
                break;
            }else{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    break;
                }
            }
        }
        finished = false;
    }
}
