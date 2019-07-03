package overload.balance.client.client;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

public class RequestCommand extends HystrixCommand<String> {

    private final RestTemplate restTemplate;

    protected RequestCommand(RestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey("ServerGroup"));
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://server/", String.class);
    }
}
