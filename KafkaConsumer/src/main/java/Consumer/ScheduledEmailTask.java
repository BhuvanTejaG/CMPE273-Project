package Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledEmailTask {



    private String topic;


    private String kafkaBrokerUrl;

    @Scheduled(fixedRate = 1000)
    public void consumeMessages() {
        System.out.println("The time is now " + new Date());
		//Stub Scheduler for calling Kafka consumer
    }
}
