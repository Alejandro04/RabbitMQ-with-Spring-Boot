package rabbitmqexample.rabbitmq.infra;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EventProduct {
    private static final String EVENTS = "events";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        // Open AMQ connection and implement channel
        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {

            // Create exchange fanout events
            channel.exchangeDeclare(EVENTS, BuiltinExchangeType.FANOUT);

            int count = 1;
            // Send messages to exchange fonout events
            while (true) {
                String message = "Event " + count;
                System.out.println("Creating message: " + message);
                channel.basicPublish(EVENTS, "", null, message.getBytes());
                Thread.sleep(1000);
                count++;
            }
        }
    }
}
