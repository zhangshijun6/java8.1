package cn.xdl.test;

public class Main2 {
    public static void main(String[] ages){
        Dome2 plate=new Dome2();
        new Thread(plate.new Producer(),"生产者1").start();
        new Thread(plate.new Producer(),"生产者2").start();
        new Thread(plate.new Consumer(),"消费者1").start();
        new Thread(plate.new Consumer(),"消费者2").start();
    }
}
