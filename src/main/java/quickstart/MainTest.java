package quickstart;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: za-lvjian
 * Date: 2017/8/17 10:42
 * DESC:
 */
public class MainTest {

    @Test
    public void method() {
        ExecutorService executor = Executors.newCachedThreadPool();
        int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方；
        EventFactory<LongEvent> eventFactory = new LongEventFactory();

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(eventFactory,
                ringBufferSize, executor, ProducerType.SINGLE,
                new YieldingWaitStrategy());
        EventHandler<LongEvent> eventHandler = new LongEventHandler();
        disruptor.handleEventsWith(eventHandler);

        disruptor.start();

        // 发布事件；
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        long sequence = ringBuffer.next();//请求下一个事件序号；

        try {
            LongEvent event = ringBuffer.get(sequence);//获取该序号对应的事件对象；
            long data = 888888l;//获取要通过事件传递的业务数据；
            event.set(data);
        } finally {
            ringBuffer.publish(sequence);//发布事件；
            //publish必须被调用,否则会阻塞后续发布的producer
        }
        CountDownLatch latch = new CountDownLatch(1);
        disruptor.shutdown();//关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
        executor.shutdown();//关闭 disruptor 使用的线程池；如果需要的话，必须手动关闭， disruptor 在 shutdown 时不会自动关闭；
    }
}
