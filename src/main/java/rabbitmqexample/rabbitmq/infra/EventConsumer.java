package rabbitmqexample.rabbitmq.infra;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

public class EventConsumer {

    private static final String EVENTS = "events";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        // Open connection
        Connection connection = connectionFactory.newConnection();
        // Implement channel
        Channel channel = connection.createChannel();
        // Declare exchange events
        channel.exchangeDeclare(EVENTS, BuiltinExchangeType.FANOUT);
        // Create queue and y associate it with the events exchange
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EVENTS, "");
        // Create subscription to a queue associate to the exchange events
        channel.basicConsume(queueName,
                true,
                (consumerTag, message) -> {
                    String messageBody = new String(message.getBody(), Charset.defaultCharset());
                    System.out.println("Received message: " + messageBody);
                },
                consumerTag -> {
                    System.out.println("Consumer " + consumerTag + " canceled");
                });
    }
}
