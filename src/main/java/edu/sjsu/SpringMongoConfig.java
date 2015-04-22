package edu.sjsu;

/**
 * Created by vipul on 4/13/2015.
 */

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Spring MongoDB configuration file
 *
 * Created by vipul on 4/19/2015.
 *
 */
@Configuration
public class SpringMongoConfig {

    public @Bean
    MongoTemplate mongoTemplate() throws Exception {


        MongoClient mongoClient = new MongoClient("ds061661.mongolab.com:61661");
        DB db = mongoClient.getDB("linkedin");
        boolean auth = db.authenticate("vipul", "vipul123".toCharArray());

        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient,"linkedin");
        return mongoTemplate;

    }

}
