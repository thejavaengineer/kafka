package org.thejavaengineer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

public class UserInputKafkaProducer {
    public static void main(String[] args) {
        // Configure Kafka producer properties
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // Create Kafka producer instance
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // Create a Scanner to read user input
        Scanner scanner = new Scanner(System.in);
        String message;

        // Prompt user to enter messages continuously
        System.out.println("Enter messages to send to Kafka (type 'exit' to stop):");

        // Continuously take input from user until 'exit' is typed
        while (true) {
            System.out.print("Enter message: ");
            message = scanner.nextLine();

            if (message.equalsIgnoreCase("exit")) {
                break;  // Exit the loop and stop producing
            }

            // Create a ProducerRecord and send the message to Kafka
            ProducerRecord<String, String> record = new ProducerRecord<>("my-topic", null, message);
            String finalMessage = message;
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    System.out.println("Error sending message: " + exception.getMessage());
                } else {
                    System.out.println("Message sent successfully: " + finalMessage);
                }
            });
        }

        // Close resources
        producer.close();
        scanner.close();
        System.out.println("Producer closed. Application ended.");
    }
}
