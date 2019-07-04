package overload.balance.wait_for;


import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.IOException;

import static java.lang.System.exit;

public class WaitFor {

    private Logger logger = Logger.getLogger(WaitFor.class);

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("You should define url");
            exit(0);
        }
        new WaitFor().start(args[0]);
    }

    private void start(String url) {
        System.out.println("Wait for " + url);
        CloseableHttpResponse response = null;
        while (true) {
            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                HttpGet httpGet = new HttpGet(url);
                response = httpclient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    System.out.println("Got response from " + url);
                    exit(0);
                }
            } catch (RuntimeException e) {
                //do nothing
            } catch (ClientProtocolException e) {
                //do nothing
            } catch (IOException e) {
                //do nothing
            } finally {
                finishConnection(response);
            }
            pause();
        }
    }

    private void finishConnection(CloseableHttpResponse response) {
        try {
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            //do nothing
        }
    }

    private void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //do nothing
        }
    }
}
