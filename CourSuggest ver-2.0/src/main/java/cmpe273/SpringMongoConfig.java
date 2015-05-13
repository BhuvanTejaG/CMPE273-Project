package cmpe273;

/**
 * Created by vipul on 4/13/2015.
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.*;
import com.mongodb.MongoClient;

/**
 * Spring MongoDB configuration file
 *
 */
@Configuration
public class SpringMongoConfig{

    public @Bean
    MongoTemplate mongoTemplate() throws Exception {


        MongoClient mongoClient = new MongoClient("ds061651.mongolab.com:61651");
        DB db = mongoClient.getDB("cmpe273");
        boolean auth = db.authenticate("vipul", "vipul123".toCharArray());

        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient,"cmpe273");
        return mongoTemplate;

    }

}
