/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2018/12/30 16:27
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/30 16:27
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class ThreadService {
    private Thread executorThread;

    private boolean finish = false;

    public void execute(Runnable task){
        executorThread = new Thread(){
            @Override
            public void run() {
                Thread runner =new Thread(task);
                runner.setDaemon(true);
                runner.start();
                try {
                    runner.join();
                    finish = true;
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }
        };

        executorThread.start();
    }

    public void shutDown(long mills){
        long currentTimestamp = System.currentTimeMillis();
        while(!finish){
            if(System.currentTimeMillis() - currentTimestamp >= mills){
                System.out.println("任务超市，需要结束他！");
                executorThread.interrupt();
                break;
            }else{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("线程执行被打断！");
                    break;
                }
            }
        }
        finish =false;
    }
}
