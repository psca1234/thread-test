/**
 * @Description: Thread创建线程对象API
 * @Author: pansc
 * @CreateDate: 2018/12/30 0:31
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/30 0:31
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class CreateThread {
    public static void main(String [] args){
        Thread t1 =new Thread();
        System.out.println("当前线程名称为："+Thread.currentThread().getName());
        System.out.println("当前线程名称为："+t1.getName());
        t1.start();
        System.out.println("线程名称为："+t1.getName());
        Thread t2 = new Thread();
        System.out.println(t2.getName());
        Thread t3 = new Thread("MyThread1");
        System.out.println(t3.getName());
        Thread t4 = new Thread();
        System.out.println(t3.getName().equals(t4.getName()));

        /**
         * 需要注意的是，一旦指定了自定义的线程名字，
         * 就不会消耗默认线程名称命名规则中计数器
         */
    }
}
