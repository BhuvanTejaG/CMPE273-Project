package cmpe273;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by vipul on 5/11/2015.
 */
@Component
public class KafkaScheduler {

    Mailer emailService=new Mailer();

    @Scheduled(fixedRate = 5000)
    public void consumeMessages() {
        System.out.println("The time is now " + new Date());
        //KafkaMailConsumer kafkaConsumer = new KafkaMailConsumer("54.153.29.218:2181", "group", "Course", emailService);
      //  KafkaMailConsumer kafkaConsumer = new KafkaMailConsumer("Course");

      //  kafkaConsumer.consume();
    }
}
