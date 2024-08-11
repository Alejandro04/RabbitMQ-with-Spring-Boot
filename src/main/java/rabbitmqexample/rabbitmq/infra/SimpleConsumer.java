package rabbitmqexample.rabbitmq.infra;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

public class SimpleConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {

        // Open AMQ connection
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();

        // Implement channel
        Channel channel = connection.createChannel();

        // Implement first-queue
        channel.queueDeclare("first-cola", false, false, false, null);

        // Create subscription to the "first-queue" using the Basic.consume command
        channel.basicConsume("first-queue",
                true,
                (consumerTag, message) -> {
                    String messageBody = new String(message.getBody(), Charset.defaultCharset());

                    System.out.println("Message: " + messageBody);
                    System.out.println("Exchange: " + message.getEnvelope().getExchange());
                    System.out.println("Routing key: " + message.getEnvelope().getRoutingKey());
                    System.out.println("Delivery tag: " + message.getEnvelope().getDeliveryTag());
                },
                consumerTag -> {
                    System.out.println("Consumer " + consumerTag + " canceled");
                });
    }
}
