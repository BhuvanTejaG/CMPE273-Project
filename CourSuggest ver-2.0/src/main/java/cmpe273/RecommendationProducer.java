package cmpe273;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import model.Coursera;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vipul on 5/11/2015.
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.mongodb.*;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


//@Component
public class RecommendationProducer {
    ApplicationContext context= new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    MongoOperations mongoOperations= (MongoOperations)context.getBean("mongoTemplate");
    private final static Logger LOGGER = Logger.getLogger(RecommendationProducer.class.getName());

    //   Log4JLogger  log4JLogger= new Log4JLogger(RecommendationProducer.class.getName());


    //  @Scheduled(fixedRate = 1000)
    public void KafkaThread(Coursera courses,String email) {
        LOGGER.setLevel(Level.INFO);

        long events = 22;
        Random rnd = new Random();

        Properties props = new Properties();
        props.put("metadata.broker.list", "54.153.29.218:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
      //  props.put("partitioner.class", "polling.SimplePartitioner");
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);

        Producer<String, String> producer = new Producer<String, String>(config);

      //  KeyedMessage<String, String> data = new KeyedMessage<String, String>("Course", "Course suggestion",courses.getName()+"#$"+courses.getShortdescription()+"#$"+courses.getUrl()+"#$"+email);
       
	  KeyedMessage<String, String> data = new KeyedMessage<String, String>("Course", "Course suggestion",email+":"+courses.getShortName());
	
	 System.out.println("data is :"+data);
        producer.send(data);




    }



}