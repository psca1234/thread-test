import java.util.Optional;

/**
 * @Description: 暴力的方式停止线程
 * @Author: pansc
 * @CreateDate: 2019/1/9 14:23
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/9 14:23
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class TestStopThreadFource {
    public static void main(String[] args){
        ThreadService1 service1 = new ThreadService1();
        Long startTime = System.currentTimeMillis();
        service1.excute(()->{
            while (true){
                Optional.of("目标任务正在执行中。。。。。。。。").ifPresent(System.out :: println);
            }
        });
        service1.shutdown(1_000);
        Long endTime = System.currentTimeMillis();
        Optional.of("任务强制结束，耗时为："+(endTime-startTime)).ifPresent(System.out :: println);
    }
}