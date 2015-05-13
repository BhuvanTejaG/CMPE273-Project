package cmpe273;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import model.Coursera;
import model.Skills;
import model.StackOverFlow;
import model.StackOverFlowOutPut;
import model.StackOverFlowParentModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;



import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.*;

import javax.inject.Inject;

/**
 * Created by Suraj Pandey on 5/9/2015.
 */
@ComponentScan("model")
@Controller
@SessionAttributes("userObj")

public class StackOverFlowController {
	
	
	/*
	 * spring.social.linkedin.appId=757vh0r02v3ygy
spring.social.linkedin.appSecret=H89rdVfjCiouMXzS
	 */
	
	public LinkedIn linkedIn;
    public ConnectionRepository connectionRepository;

    @Inject
    public StackOverFlowController(LinkedIn linkedIn, ConnectionRepository connectionRepository) {
        this.linkedIn = linkedIn;
        this.connectionRepository = connectionRepository;
    }
	

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String submitForm(@ModelAttribute StackOverFlow stackoverflow, @ModelAttribute StackOverFlowOutPut stackOverFlowOutPut,
                             Model model)  {
     //this will be used for REST service - POST
		
        return NULL; 
	 


    }    
      
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String stackOverFlowForm(Model model, StackOverFlow stackoverflow) {
    	
    
    	
        if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) 
        {
        	return "redirect:/connect/linkedin";
        	
        }
    	
    	
				// LinkedIn Skills
				 model.addAttribute(linkedIn.profileOperations().getUserProfile());
				 List<LinkedInProfile> connections = linkedIn.connectionOperations().getConnections();
				 ArrayList<String> Linkedskills=(ArrayList<String>) linkedIn.profileOperations().getUserProfileFull().getSkills();
				 model.addAttribute("connections", connections);
				 model.addAttribute("Linkedskills",Linkedskills);
				//LinkedIn Skills
				 
				 String userEmail = linkedIn.profileOperations().getUserProfileFull().getEmailAddress();
				 
				 System.out.println("----Emil Id isss::::-------"+userEmail);
				 
				 model.addAttribute("userEmail",userEmail);
			
        
    	
    	
    	
        StackOverFlow stackOverFlow = new StackOverFlow();
        /*
         * Replaced this list with list of Stack Over Flow skills populate it.
         * It will call with get request. Or when the url is called first time.
         */
        List<String> skills = new ArrayList<String>();
        skills=stackOverFlowData();
       /* skills.add("Java");
        skills.add("Python");
        skills.add("Scala");*/
        stackOverFlow.setSkillList(skills);
        model.addAttribute("stackOverFlow", stackOverFlow);
        return "stackOverFlow";

    }
    
     
    
    
    
    
    

}
