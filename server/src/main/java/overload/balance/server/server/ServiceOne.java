package overload.balance.server.server;

import org.springframework.stereotype.Service;


@Service
public class ServiceOne {

    private int delay;

    public int getDelay() {
        int result = delay++;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
