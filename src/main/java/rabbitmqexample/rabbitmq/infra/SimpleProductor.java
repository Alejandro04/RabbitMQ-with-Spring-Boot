package rabbitmqexample.rabbitmq.infra;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SimpleProductor {

    public static void main(String[] args) throws IOException, TimeoutException {

        String message = "Hello!";
        System.out.println(message);

        // Open AMQ connection and establish channel
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            // Create queue
            String queueName = "first-queue";
            channel.queueDeclare(queueName, false, false, false, null);
            // Send message to exchange
            channel.basicPublish("", queueName, null, message.getBytes());
        }
    }
}
