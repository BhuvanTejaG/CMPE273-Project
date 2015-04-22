package edu.sjsu;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
/**
 * Created by vipul on 4/17/2015.
 */
@Controller
@RequestMapping("/")
public class LinkedInController {

    private LinkedIn linkedIn;
    ApplicationContext context= new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    MongoOperations mongoOperations= (MongoOperations)context.getBean("mongoTemplate");

    private ConnectionRepository connectionRepository;

    @Inject
    public LinkedInController(LinkedIn linkedIn, ConnectionRepository connectionRepository) {
        this.linkedIn = linkedIn;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String helloLinkedIn(Model model) {
        if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
            return "redirect:/connect/linkedin";
        }

        model.addAttribute(linkedIn.profileOperations().getUserProfile());

        List<LinkedInProfile> connections = linkedIn.connectionOperations().getConnections();
        ArrayList<String> skills=(ArrayList<String>) linkedIn.profileOperations().getUserProfileFull().getSkills();

        Skills skillpersist= new Skills();
        skillpersist.setSkill(skills);
        skillpersist.setUserId(linkedIn.profileOperations().getUserProfileFull().getFirstName() + " " + linkedIn.profileOperations().getUserProfileFull().getLastName()+" "+linkedIn.profileOperations().getProfileId());
        mongoOperations.save(skillpersist);
        
        
        

        model.addAttribute("connections", connections);
        model.addAttribute("skills",skills);
        
        return "helloskills";
    }

    @RequestMapping(value = "/connections",method=RequestMethod.GET)
    public String onluConnections(Model model) {
        if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
            return "redirect:/connect/linkedin";
        }

        model.addAttribute(linkedIn.profileOperations().getUserProfile());

        List<LinkedInProfile> connections = linkedIn.connectionOperations().getConnections();

        model.addAttribute("connections", connections);
        return "connections";
    }
    
    
    @RequestMapping(value = "courses/display",method=RequestMethod.POST)
    public String getCourses(@RequestParam("id") String skill,Model model) throws UnknownHostException{
        

		final DBCollection courses;
		
		MongoDriver connection = new MongoDriver();
		
		DB db = connection.getMongoConnection();
		
		 courses=db.getCollection("courses");
		 
		 System.out.println("Hello::::::::"+skill);	
		
		String stringPattern = ".*"+skill+".*";
		
		List<Course> courseList = new ArrayList<Course>();
		String dataExists= "true";
		
		
		//ModelAndView model = new ModelAndView("formPage");
				
		Pattern namesRegex = Pattern.compile(stringPattern);
		
        DBObject clause1 = new BasicDBObject("name", namesRegex);  
        DBObject clause2 = new BasicDBObject("shortName", namesRegex); 
        //DBObject clause3 = new BasicDBObject("shortDescription", namesRegex);
        BasicDBList or = new BasicDBList();
        or.add(clause1);
        or.add(clause2);
        //or.add(clause3);
        BasicDBObject query = new BasicDBObject("$or", or);
        
        DBCursor cursor = courses.find(query);
       
            System.out.println("***** Output *****");
             while (cursor.hasNext()) {
            	Course details =new Course();
            	
            	dataExists="false";
            	
            	
            	DBObject obj=cursor.next();
            	Double id=(Double) obj.get("id");
            	
            	int num = Integer.parseInt(String.valueOf(id).split("\\.")[0]);
            	details.setId(num);
            	
            	String shortName=(String) obj.get("shortName");
            	details.setShortName(shortName);
            	
               	String name=(String) obj.get("name");
               	details.setName(name);
            	
            	String desc=(String) obj.get("shortDescription");
            	details.setDesc(desc);
            	
            	courseList.add(details);
            	

        }
		
		model.addAttribute("courseList", courseList);
		
		return "courses";


    }

}
