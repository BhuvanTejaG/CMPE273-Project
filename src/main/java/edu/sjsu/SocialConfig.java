package edu.sjsu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;

/**
 * Created by vipul on 4/20/2015.
 */
@Configuration
public class SocialConfig {

    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
       // registry.addConnectionFactory(new LinkedInConnectionFactory(
         //       .getProperty("linkedin.consumerKey"),
         //       environment.getProperty("linkedin.consumerSecret")));
        return registry;
    }

}