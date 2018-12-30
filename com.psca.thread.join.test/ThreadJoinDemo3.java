/**
 * @Description: 这是一个join的综合练习
 * @Author: pansc
 * @CreateDate: 2018/12/30 14:24
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/30 14:24
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class ThreadJoinDemo3 {
    public static void main(String [] args)throws InterruptedException{
        Long startTimestamp = System.currentTimeMillis();
        MachineInfoRunnableClass machine1 =new MachineInfoRunnableClass("machine1",10_000);
        MachineInfoRunnableClass machine2 =new MachineInfoRunnableClass("machine2",30_000);
        MachineInfoRunnableClass machine3 =new MachineInfoRunnableClass("machine3",15_000);
        Thread t1 = new Thread(machine1);
        Thread t2 = new Thread(machine2);
        Thread t3 = new Thread(machine3);
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        Long endTimestamp = System.currentTimeMillis();
        System.out.printf("all tasks has runed end,begins %s,ends %s\n",startTimestamp,endTimestamp);
    }
}

class MachineInfoRunnableClass implements Runnable{
    private String machineName;
    private long sleepMillions;

    public MachineInfoRunnableClass(String machineName, long sleepMillions) {
        this.machineName = machineName;
        this.sleepMillions = sleepMillions;
    }

    @Override
    public void run() {
        try {
            System.out.printf("%s is running ...\n",machineName);
            System.out.printf("%s get os logging\n",machineName);
            Thread.sleep(sleepMillions);
            System.out.printf("%s has runed successful...\n",machineName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
