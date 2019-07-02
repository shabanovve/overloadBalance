package overload.balance.server.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class Controller {

    @Autowired
    private ServiceOne service;

    private Logger logger = Logger.getLogger(Controller.class.getName());

    @RequestMapping("/")
    public int getDelay() {
        logger.info("Handle call");
        return service.getDelay();
    }
}
