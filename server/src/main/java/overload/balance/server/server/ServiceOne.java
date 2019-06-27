package overload.balance.server.server;

import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class ServiceOne {

    private int delay;
    private Instant last = Instant.now();

    public int getDelay() {
        incrementDelay();
        decrimentDelay();

        int result = delay;
        try {
            Thread.sleep(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private void decrimentDelay() {
        Instant current = Instant.now();
        long diff = current.getEpochSecond() - last.getEpochSecond();
        int dec = (int) (diff * 3);
        if (delay > 0 && dec > 0) {
            delay = (delay - dec) < 0 ? 0 : delay - dec;
        }
        last = current;
    }

    private void incrementDelay() {
        if (delay < 1000) {
            delay = delay + 20;
        }
    }
}
