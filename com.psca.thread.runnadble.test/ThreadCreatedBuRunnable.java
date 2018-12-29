/**
 * @Description: 使用继承Runnable接口的方式创建线程
 * @Author: pansc
 * @CreateDate: 2018/12/29 20:08
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/29 20:08
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class ThreadCreatedBuRunnable implements Runnable{
    private int index=0;
    //此处使用静态类型，保证了在不引用线程新增关键字的基础下，多个线程可以使用相同的一份数据
    private static final int MAX =50;


    @Override
    public void run() {
        while(index<=MAX){
            System.out.println("当前是"+Thread.currentThread().getName()+"呼叫号码为："+index++);
        }
    }
}
