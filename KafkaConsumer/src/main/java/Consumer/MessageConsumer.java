package Consumer;

/**
 * Created by vipul on 5/10/2015.
 */

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;
import java.util.*;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;
/*
public class KafkaMailConsumer {
    private static String USER_NAME = "cloudwatchalarm";
    private static String PASSWORD = "admin123!@#";
    private static final Logger LOG = LoggerFactory.getLogger(KafkaMailConsumer.class);

    private final Mailer emailService;
    private final ConsumerConnector consumer;
    private final String topic;

    public KafkaMailConsumer(String zookeeper, String groupId, String topic, Mailer emailService) {
        this.emailService = emailService;
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeper);
        props.put("group.id", groupId);
        props.put("zookeeper.session.timeout.ms", "500");
        props.put("zookeeper.sync.time.ms", "250");
        props.put("auto.commit.interval.ms", "1000");

        consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
        this.topic = topic;
    }

    public void consume() {
        Map<String, Integer> topicCount = new HashMap<String,Integer>();
        topicCount.put(topic, 1);

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumer.createMessageStreams(topicCount);
        List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topic);
        for (final KafkaStream stream : streams) {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext()) {
                String msg = new String(it.next().message());
                System.out.println("---------------finally reached here------------");
                LOG.debug("##### Message from Topic: {} #########", msg);
                List<String> result = parseMsg(msg);
                String[] to ={"cloudwatchalarm@gmail.com"};
                //if (result.size() > 2) {
                emailService.sendFromGMail(USER_NAME,PASSWORD,to,msg,"body");
               // } else {
                    LOG.error("Invalid message format:{}", msg);
                //}
            }
        }
        if (consumer != null) {
          //  consumer.shutdown();
        }
    }

    private List<String> parseMsg(String msg) {
        List<String> result = new ArrayList();

        if (StringUtils.isEmpty(msg)) {
            return result;
        }

        for (String each : msg.split(":")) {
            result.add(each);
        }

        return result;
    }

}*/


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
//import kafka.consumer.KafkaMessageStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class MessageConsumer
{
    private static final Logger LOG = LoggerFactory.getLogger(MessageConsumer.class);
    private final ConsumerConnector consumer;
    private final String topic;

    public MessageConsumer(String topic)
    {
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig());
        this.topic = topic;
    }

    private static ConsumerConfig createConsumerConfig()
    {
        Properties props = new Properties();
        props.put("zookeeper.connect", "54.153.29.218:2181");
        props.put("group.id", "group1");
        props.put("zookeeper.session.timeout.ms", "500");
        props.put("zookeeper.sync.time.ms", "250");
        props.put("auto.commit.interval.ms", "1000");


        return new ConsumerConfig(props);

    }

    public void consume() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(1));
        Mailer mailer= new Mailer();
       /* Map<String, List<KafkaStream>> consumerMap = consumer.createMessageStreams(topicCountMap);
        KafkaMessageStrea stream =  consumerMap.get(topic).get(0);
        ConsumerIterator it = stream.iterator();
        while(it.hasNext())
            System.out.println(ExampleUtils.getMessage(it.next()));
    */
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topic);
        LOG.info("inside consume");
        for (final KafkaStream stream : streams) {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
           while (it.hasNext()) {
                String test = new String(it.next().message());
                List<String> result = parseMsg(test);
                System.out.println("messages are : "+ test);
                LOG.info("messages are :",test);
                String[] to ={result.get(0)};
                if (result.size() > 1) {
//                mailer.sendFromGMail("coursuggest","admin123!@#",to,"Coursuggest recommendations at ","Course name :"+result.get(0)+"\nCourse Description :"+result.get(1)+"\nCourse url :"+result.get(2));
                mailer.sendFromGMail("coursuggest","admin123!@#",to,"Coursuggest recommendations ","Hi ,\n This is the link to your course suggestion : https://www.coursera.org/course/"+ result.get(1)+" . \n Go ahead and access your course using this url");

                 }
            }
        }
       /* if (consumer != null) {
            consumer.shutdown();
        }*/
    }
    private List<String> parseMsg(String msg) {
        List<String> result = new ArrayList();

        if (StringUtils.isEmpty(msg)) {
            return result;
        }

        for (String each : msg.split(":")) {
            result.add(each);
	    System.out.println("split files : "+each );


        }

        return result;
    }
 /*   public String getMessage(Byte[] message)
    {
        ByteBuffer buffer = message;
        byte [] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return new String(bytes);
    }*/
}