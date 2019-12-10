package cn.xdl.test;

public class Demo1 {
    private final static String LOCK="locak";
    private int count=0;//容器中生产个数
    private static final int FULL=10;//容器中最大存放个数
    public static void main(String[] args){

        Demo1 plate=new Demo1();
            new Thread(plate.new Producer(),"生产者1").start();
            new Thread(plate.new Producer(),"生产者2").start();
            new Thread(plate.new Consumer(),"消费者1").start();
            new Thread(plate.new Consumer(),"消费者2").start();
    }

    public class Producer implements Runnable{

        public void run() {
            for(int i=0;i<10;i++){
                try {
                    Thread.sleep(350);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK) {
                    while (count == FULL) {
                        System.out.println("生产达到最大值");
                        try {
                            //当前线程等待
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println("生产者："+Thread.currentThread().getName()+"总共有"+count+"个资源");
                    //唤醒在此等待的全部线程
                    LOCK.notifyAll();
                }
            }
        }
    }
    public class Consumer implements Runnable {

        public void run() {

            for (int i = 0; i < 10; i++) {

                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (LOCK) {

                    while (count == 0) {
                        System.out.println("产品为空");
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    count--;
                    System.out.println("消费者 " + Thread.currentThread().getName() + " 总共有 " + count + " 个资源");
                    LOCK.notifyAll();

                }

            }

        }

    }
}
