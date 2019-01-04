/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2018/12/30 16:39
 * @UpdateUser: pansc
 * @UpdateDate: 2018/12/30 16:39
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class TestThreadService {
    public static void main(String[] args) {
        ThreadService threadService = new ThreadService();
        long startTimestamp = System.currentTimeMillis();
        threadService.execute(() -> {
            while (true) {

            }
        });
        threadService.shutDown(10_000);
        long endTimestamp = System.currentTimeMillis();
        System.out.printf("暴力结束线程所用时间为：%s\n", endTimestamp - startTimestamp);
    }
}
