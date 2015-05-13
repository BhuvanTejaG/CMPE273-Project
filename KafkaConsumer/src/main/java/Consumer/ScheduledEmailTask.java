package Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by saung on 4/7/15.
 */
@Component
public class ScheduledEmailTask {



    private String topic;


    private String kafkaBrokerUrl;

    @Scheduled(fixedRate = 1000)
    public void consumeMessages() {
        System.out.println("The time is now " + new Date());
       // KafkaConsumer kafkaConsumer = new KafkaConsumer("54.153.29.218:2181", "test-group", "Course", emailService);
        MessageConsumer kafkaConsumer = new MessageConsumer("Course");
        kafkaConsumer.consume();
    }
}
