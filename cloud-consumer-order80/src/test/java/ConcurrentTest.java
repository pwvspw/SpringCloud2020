import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentTest {

    private final AtomicInteger atomicInteger = new AtomicInteger(2);

    @Test
    public void test1() {

        boolean b = atomicInteger.compareAndSet(2, 3);
        System.out.println(b);

        int i = atomicInteger.get();
        System.out.println(i);

    }

}
