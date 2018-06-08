package net.lessons.nats;

import io.nats.streaming.Message;
import io.nats.streaming.MessageHandler;
import io.nats.streaming.NatsStreaming;
import io.nats.streaming.Options;
import io.nats.streaming.StreamingConnection;
import io.nats.streaming.Subscription;
import io.nats.streaming.SubscriptionOptions;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Subscriber {
    private String url = "http://localhost:5667";
    private String subject = "subject";
    private String clusterId = "test-cluster";
    private String clientId = "test-subscriber";
    private final SubscriptionOptions.Builder builder = new SubscriptionOptions.Builder();
    private String qgroup = "test-group";
    private String durable = "test-durable";
    private int count = 0;
    private boolean unsubscribe;

    private String bitesToChar(byte[] bytes){
      StringBuilder sb = new StringBuilder();
      int len = 0;
      if (bytes != null) {
          len = bytes.length;
      }
      for (int i = 0; i < len; i++) {
          sb.append((char) bytes[i]);
      }
      return sb.toString();
    }

    private void run() throws Exception {
        //builder.deliverAllAvailable();
        builder.durableName(durable);
        Options opts = null;
        if (url != null) {
          System.out.println("URL IS: "+url);
            opts = new Options.Builder().natsUrl(url).build();
        }

        final CountDownLatch done = new CountDownLatch(1);
        final CountDownLatch start = new CountDownLatch(1);
        final AtomicInteger delivered = new AtomicInteger(0);

        Thread hook = null;

        try (final StreamingConnection sc = NatsStreaming.connect(clusterId, clientId, opts)) {
            try {
                final Subscription sub = sc.subscribe(subject, qgroup, new MessageHandler() {
                    public void onMessage(Message msg) {
                        try {
                            start.await();
                        } catch (InterruptedException e) {
                            /* NOOP */
                        }
                        System.out.printf("[#%d] Received on [%s]: '%s'\n",
                                delivered.incrementAndGet(), msg.getSubject(), bitesToChar(msg.getData()));
                        if (delivered.get() == count) {
                            done.countDown();
                        }
                    }
                }, builder.build());
                hook = new Thread() {
                    public void run() {
                        System.err.println("\nCaught CTRL-C, shutting down gracefully...\n");
                        try {
                            if (durable == null || durable.isEmpty() || unsubscribe) {
                                sub.unsubscribe();
                            }
                            sc.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        done.countDown();
                    }
                };
                Runtime.getRuntime().addShutdownHook(hook);
                System.out.printf("Listening on [%s], clientID=[%s], qgroup=[%s] durable=[%s]\n",
                        sub.getSubject(), clientId, sub.getQueue(),
                        sub.getOptions().getDurableName());
                start.countDown();
                done.await();
                if (durable == null || durable.isEmpty() || unsubscribe) {
                    sub.unsubscribe();
                }
                sc.close();
            } finally {
                Runtime.getRuntime().removeShutdownHook(hook);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            new Subscriber().run();
        } catch (IllegalArgumentException e) {
            System.out.flush();
            System.err.println(e.getMessage());
            System.err.flush();
            throw e;
        }
    }

}
