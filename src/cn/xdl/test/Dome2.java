package cn.xdl.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dome2 {
    private int code =0;//容器中生产个数
    private static final int FULL=10;//容器中最大存放个数
    private final Lock lock=new ReentrantLock();
    //仓库条件变量满
    private final Condition fullCondition=lock.newCondition();
    //仓库条件变量空
    private final Condition emptyCondition=lock.newCondition();
    public class Producer implements Runnable{
        @Override
        public void run() {
            for(int i=0;i<10;i++){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //上锁
                lock.lock();
                while(FULL==code){
                    System.out.println("仓库已满");
                    try {
                        fullCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                code ++;
                System.out.println("生产者："+Thread.currentThread().getName()+"总共有"+code+"个资源");
                //唤醒等待线程
                emptyCondition.signalAll();
                //解锁
                lock.unlock();
            }
        }
    }
    public class Consumer implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<10;i++){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                while(code==0){
                    System.out.println("仓库为空");
                    try {
                        emptyCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                code--;
                System.out.println("消费者 " + Thread.currentThread().getName() + " 总共有 " + code + " 个资源");
                fullCondition.signalAll();
                lock.unlock();
            }
        }
    }
}
