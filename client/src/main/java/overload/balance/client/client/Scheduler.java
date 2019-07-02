package overload.balance.client.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Component
public class Scheduler {
    private Logger logger = Logger.getLogger(ClientApplication.class.getName());
    private String serverOne;
    private String serverTwo;
    private String server;
    private List<ServiceInstance> instances;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Scheduled(fixedDelay = 1000)
    private void process() throws InterruptedException {
        if (instances == null) {
            instances = getInstances();
            serverOne = instances.get(0).getHost();
            serverTwo = instances.get(1).getHost();
            this.server = serverOne;
        }

        try {
            URL url = new URL(String.format("http://%s:8080", server));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setConnectTimeout(1000);
            con.setReadTimeout(1000);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            logger.info("Response " + content);
        } catch (ProtocolException e) {
            logger.severe(e.getMessage());
            changeServer();
        } catch (MalformedURLException e) {
            logger.severe(e.getMessage());
            changeServer();
        } catch (IOException e) {
            logger.severe(e.getMessage());
            changeServer();
        }
    }

    private List<ServiceInstance> getInstances() throws InterruptedException {
        List<ServiceInstance> instances = Collections.emptyList();
        for (int attemp = 0; attemp <= 180; attemp++) {
            if (discoveryClient != null) {
                instances = discoveryClient.getInstances("server");
                if (instances.size() == 2) {
                    break;
                }
            }
            Thread.sleep(1000);
        }
        if (instances.size() != 2) {
            throw new RuntimeException("Found server instances = " + instances.size());
        }
        return instances;
    }

    private void changeServer() {
        if (server == serverOne) {
            server = serverTwo;
        } else {
            server = serverOne;
        }
        logger.info("Change server to " + server);
    }

}
