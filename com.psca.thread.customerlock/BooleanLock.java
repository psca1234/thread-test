import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * @Description: java类作用描述
 * @Author: pansc
 * @CreateDate: 2019/1/9 17:58
 * @UpdateUser: pansc
 * @UpdateDate: 2019/1/9 17:58
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class BooleanLock implements Lock{
    private boolean initValue;
    private Collection<Thread> booleanBlockedThreads = new ArrayList<>();
    private Thread targetThread;
    public BooleanLock(){
        this.initValue = false;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        while(initValue){
            this.wait();
            booleanBlockedThreads.add(Thread.currentThread());
        }
        initValue = true;
        booleanBlockedThreads.remove(Thread.currentThread());
        Optional.of(Thread.currentThread().getName()+" get this booleanLock!").ifPresent(System.out :: println);
        this.targetThread = Thread.currentThread();
    }

    @Override
    public synchronized void lock(long millons) throws InterruptedException,TimeoutExcetion{
        if(millons <=0){
            lock();
        }
        long timeoutMillions =millons;
        long endMillions = System.currentTimeMillis() + millons;
        while (initValue){
            if(timeoutMillions <=0){
                throw new TimeoutExcetion(Thread.currentThread().getName()+" Time out");
            }
            this.wait(millons);
            timeoutMillions = endMillions-System.currentTimeMillis();
        }
        this.initValue=true;
        this.targetThread = Thread.currentThread();

    }

    @Override
    public synchronized void unlock() {
        if(Thread.currentThread() == targetThread){
            this.initValue = false;
            Optional.of(Thread.currentThread().getName()+" has released this locked").ifPresent(System.out :: println);
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockedThreads() {
        return Collections.unmodifiableCollection(booleanBlockedThreads);
    }

    @Override
    public Integer getBlockedThreadSize() {
        return booleanBlockedThreads.size();
    }
}
