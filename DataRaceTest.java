import org.junit.Test;
import static org.junit.Assert.assertFalse;

public class DataRaceTest {
    private volatile boolean buggy = false;

    @Test
    public void test() throws InterruptedException {
        DataRace.request();

        int threadsNum = 3;
        Thread[] ts = new Thread[threadsNum];

        for (int i = 0; i < threadsNum; i++) {
            ts[i] = new Thread(new Task());
            ts[i].start();
        }

        for (int i = 0; i < threadsNum; i++) {
            ts[i].join();
        }

        assertFalse(buggy);
    }

    private class Task implements Runnable {
        @Override
        public void run() {
            for (int iter = 0; iter < 100; iter++) {
                DataRace.request();
                for (int i = 0; i < DataRace.rep.length; i++) {
                    if (DataRace.rep[i] == null) {
                        buggy = true;
                        return;
                    }
                }
            }
        }
    }
}
