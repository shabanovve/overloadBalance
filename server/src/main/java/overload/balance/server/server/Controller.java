package overload.balance.server.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private ServiceOne service;

    @RequestMapping("/")
    public int getDelay() {
        return service.getDelay();
    }
}
