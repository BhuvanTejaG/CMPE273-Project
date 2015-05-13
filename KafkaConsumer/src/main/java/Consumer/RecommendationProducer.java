package Consumer;


import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vipul on 5/11/2015.
 */



//@Component
public class RecommendationProducer {
   private final static Logger LOGGER = Logger.getLogger(RecommendationProducer.class.getName());



   // @Scheduled(fixedRate = 1000)
    public void KafkaThread() {
        LOGGER.setLevel(Level.INFO);

        long events = 22;
        Random rnd = new Random();

        Properties props = new Properties();
        props.put("metadata.broker.list", "54.153.29.218:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        //props.put("partitioner.class", "polling.SimplePartitioner");
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);

        Producer<String, String> producer = new Producer<String, String>(config);

        KeyedMessage<String, String> data = new KeyedMessage<String, String>("Course", "Poll-Id","message from the producer");
        System.out.println("data is :"+data);
        producer.send(data);




    }



}