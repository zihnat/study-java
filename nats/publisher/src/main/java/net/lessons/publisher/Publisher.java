package net.lessons.publisher;

import io.nats.streaming.AckHandler;
import io.nats.streaming.NatsStreaming;
import io.nats.streaming.Options;
import io.nats.streaming.StreamingConnection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

public class Publisher {
    private String urls = "http://localhost:5667";
    private String subject = "subject";
    private String payloadString = "Hello from publisher";
    private String clusterId = "test-cluster";
    private String clientId = "test-publisher";
    private boolean async;

    public void run() throws Exception {
        Options opts = NatsStreaming.defaultOptions();
        if (urls != null) {
            opts = new Options.Builder().natsUrl(urls).build();
        }

        try (StreamingConnection sc = NatsStreaming.connect(clusterId, clientId, opts)) {
          for(int i = 0; i<100 ; i++){
            Thread.sleep(1000);
            final CountDownLatch latch = new CountDownLatch(1);
            final String[] guid = new String[1];
            String payloadStr = payloadString + " #" + i;
            byte[] payload = payloadStr.getBytes();

            AckHandler acb = new AckHandler() {
                @Override
                public void onAck(String nuid, Exception ex) {
                    System.out.printf("Received ACK for guid %s\n", nuid);
                    if (ex != null) {
                        System.err.printf("Error in server ack for guid %s: %s", nuid,
                                ex.getMessage());
                    }
                    if (!guid[0].equals(nuid)) {
                        System.err.printf(
                                "Expected a matching guid in ack callback, got %s vs %s\n", nuid,
                                guid[0]);
                    }
                    System.out.flush();
                    latch.countDown();
                }
            };

            if (!async) {
                try {
                    //noinspection ConstantConditions
                    sc.publish(subject, payload);
                } catch (Exception e) {
                    System.err.printf("Error during publish: %s\n", e.getMessage());
                    throw (e);
                }
                System.out.printf("Published [%s] : '%s'\n", subject, payloadStr);
            } else {
                try {
                    //noinspection ConstantConditions
                    guid[0] = sc.publish(subject, payload, acb);
                    latch.await();
                } catch (IOException e) {
                    System.err.printf("Error during async publish: %s\n", e.getMessage());
                    throw (e);
                }

                if (guid[0].isEmpty()) {
                    String msg = "Expected non-empty guid to be returned.";
                    System.err.println(msg);
                    throw new IOException(msg);
                }
                System.out.printf("Published [%s] : '%s' [guid: %s]\n", subject, payloadString,
                        guid[0]);
            }
          }
        } catch (IOException e) {
            if (e.getMessage().equals(io.nats.client.Nats.ERR_NO_SERVERS)) {
                String err = String.format(
                        "Can't connect: %s.\nMake sure a NATS Streaming Server is running at: %s",
                        e.getMessage(), urls);
                throw new IOException(err);
            } else {
                throw (e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            new Publisher().run();
        } catch (IllegalArgumentException e) {
            System.out.flush();
            System.err.println(e.getMessage());
            System.err.flush();
            throw e;
        }
    }
}
