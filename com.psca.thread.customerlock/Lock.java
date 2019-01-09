import java.util.Collection;

/**
 * @Description: 自定义锁接口
 * @Author: pansc
 * @CreateDate: 2019/1/9 17:52
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/9 17:52
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public interface Lock {
    static class TimeoutExcetion extends Exception{
        public TimeoutExcetion(String message){
            super(message);
        }
    }

    void lock() throws InterruptedException;
    void lock(long millons)throws InterruptedException,TimeoutExcetion;
    void unlock()throws InterruptedException;
    Collection<Thread> getBlockedThreads();
    Integer getBlockedThreadSize();
}
