package overload.balance.client.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Logger;

@SpringBootApplication
public class ClientApplication {

    private static Logger logger = Logger.getLogger(ClientApplication.class.getName());
    private static Server server = Server.ONE;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ClientApplication.class, args);

        while (true) {
            try {
                URL url = new URL(String.format("http://%s:8080", server.getName()));
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                Thread.sleep(100);

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
    }

    private static Server changeServer() {
        if (server == Server.ONE) {
            server = Server.TWO;
        } else {
            server = Server.ONE;
        }
        logger.info("Change server to " + server.getName());

        return server;
    }

}
