package overload.balance.client.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Component
@RibbonClient(name = "server", configuration = RibbonConfiguration.class)
public class Scheduler {
    private Logger logger = Logger.getLogger(ClientApplication.class.getName());

    @Autowired
    private DiscoveryClient discoveryClient;

    @LoadBalanced
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @Scheduled(fixedDelay = 1000)
    private void process() throws InterruptedException {
        if (discoveryClient.getInstances("server").size() < 2) {
            logger.info("Waiting for eureka");
        } else {
            String content = restTemplate().getForObject("http://server/", String.class);
            logger.info("Response " + content);
        }
    }
}
