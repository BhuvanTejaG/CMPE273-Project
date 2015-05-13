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
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    ApplicationContext context= new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    MongoOperations mongoOperations= (MongoOperations)context.getBean("mongoTemplate");


    public LinkedIn linkedIn;
    public ConnectionRepository connectionRepository;

    @Inject
    public StackOverFlowController(LinkedIn linkedIn, ConnectionRepository connectionRepository) {
        this.linkedIn = linkedIn;
        this.connectionRepository = connectionRepository;
    }
	

   
    private List<String> stackOverFlowData() {
        InputStream gzippedResponse = null;
        InputStream ungzippedResponse = null;
        BufferedReader bufferedReader = null;
        Reader reader = null;
        List<String> skills = new ArrayList<String>();
        try {
            URL url = new URL("https://api.stackexchange.com/2.2/tags?order=desc&sort=popular&site=stackoverflow");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            if("gzip".equalsIgnoreCase(conn.getContentEncoding())) {
                gzippedResponse = conn.getInputStream();
                ungzippedResponse = new GZIPInputStream(gzippedResponse);
                reader = new InputStreamReader(ungzippedResponse, "UTF-8");
                bufferedReader = new BufferedReader(new InputStreamReader(ungzippedResponse, "UTF-8"));
                StringBuilder strBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine())!= null) {
                    strBuilder.append(line);
                }
                System.out.println("----strBuilder------"+strBuilder.toString());
                StackOverFlowParentModel stackData = null;
                ObjectMapper mapper = new ObjectMapper();
                stackData = mapper.readValue(strBuilder.toString(), StackOverFlowParentModel.class);
                //  JSONObject result = new JSONObject("Your string here").getJSONObject("result");
                for(HashMap<String, String> item : stackData.getItems()) {
                    skills.add(item.get("name"));
                    System.out.println("----count----" + item.get("count"));
                    System.out.println("--name------" + item.get("name"));
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            System.out.println("------Error--------"+e);
        } catch (IOException e) {
            System.out.println("------Error--------" + e);
        } finally {
            if(gzippedResponse!=null) {
                try {
                    bufferedReader.close();
                    ungzippedResponse.close();
                    bufferedReader.close();
                    reader.close();
                }
                catch(IOException e) {
                    System.out.println("------Error--------" + e);
                }
            }
        }
        return skills;
    }

    @RequestMapping(value = "/courses", method = RequestMethod.POST)
    public String submitForm(@ModelAttribute StackOverFlow stackoverflow, @ModelAttribute StackOverFlowOutPut stackOverFlowOutPut,
                             Model model,@RequestParam("userEmail") String userEmail)  {
  
        System.out.println("-------TextBox--------" + stackoverflow.getSkillTextBox());
        System.out.println("-------TextBox--------" + stackoverflow.getSkillDropDown());
        System.out.println("-------UserEmail in fectching courses--------" +userEmail );
        
        stackOverFlowOutPut = new StackOverFlowOutPut();
        stackOverFlowOutPut.setSkill(stackoverflow.getSkillDropDown());
        /*
         * Replaced thie list with the mapped courses.
         */
        try{
         
          
             
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
            headers.setContentType(mediaType);
            headers.set("Accept-Encoding","GZIP");
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            String response = restTemplate.getForObject("https://api.coursera.org/api/catalog.v1/courses?fields=id,shortName,name,shortDescription&q=search&query="+stackoverflow.getSkillDropDown(),
                    String.class);
            System.out.println("----response1-------"+response);
            JSONObject jsonObject= new JSONObject(response);
            JSONArray listCourses= jsonObject.getJSONArray("elements");

                ArrayList<Coursera> courseList = new ArrayList();


            for(int i=0; i<listCourses.length();i++){
                JSONObject temp=listCourses.getJSONObject(i);
                Coursera course = new Coursera();

                course.setId(temp.getInt("id"));
                course.setName(temp.getString("name"));
                course.setShortName(temp.getString("shortName"));
                course.setShortdescription(temp.getString("shortDescription"));

                String url = "https://www.coursera.org/course/"+temp.getString("shortName");

                course.setUrl(url);

                Sessions tempSession=  mongoOperations.findOne(new Query(Criteria.where("courseId").is(temp.getInt("id"))), Sessions.class);
                if(tempSession.getActive()) {
                    courseList.add(course);
                }
             //   courseList.add(course);

            }
            model.addAttribute("stackOverFlowOutPut", stackOverFlowOutPut);
            model.addAttribute("userEmail", userEmail);
            stackOverFlowOutPut.setCourses(courseList);

            System.out.println("----response1-------"+response);

//stackOverFlowOutPut.setCourses();


            return "stackOverFlowOutPut";
        }catch(HttpClientErrorException e){

        }
        return "stackOverFlowOutPut";

    }
    
    
    @RequestMapping(value = "/linkedInCourses",method=RequestMethod.POST)
    public String getCourses(@RequestParam("id") String skill,@ModelAttribute StackOverFlow stackoverflow, @ModelAttribute StackOverFlowOutPut stackOverFlowOutPut,
            Model model,@RequestParam("userEmail") String userEmail) throws UnknownHostException{
    	
    	System.out.println("-------UserEmail in fectching linkedin courses--------" +userEmail );
    	
    	  stackOverFlowOutPut = new StackOverFlowOutPut();
          stackOverFlowOutPut.setSkill(skill);
          
          System.out.println("Skill is :::::"+skill);
        
    	try{
            
            
            
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
            headers.setContentType(mediaType);
            headers.set("Accept-Encoding","GZIP");
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            String response = restTemplate.getForObject("https://api.coursera.org/api/catalog.v1/courses?fields=id,shortName,name,shortDescription&q=search&query="+skill,
                    String.class);
            System.out.println("----response1-------"+response);
            JSONObject jsonObject= new JSONObject(response);
            JSONArray listCourses= jsonObject.getJSONArray("elements");

                ArrayList<Coursera> courseList = new ArrayList();


            for(int i=0; i<listCourses.length();i++){
                JSONObject temp=listCourses.getJSONObject(i);
                Coursera course = new Coursera();

                course.setId(temp.getInt("id"));
                course.setName(temp.getString("name"));
                course.setShortName(temp.getString("shortName"));
                course.setShortdescription(temp.getString("shortDescription"));

                String url = "https://www.coursera.org/course/"+temp.getString("shortName");

                course.setUrl(url);

                Sessions tempSession=  mongoOperations.findOne(new Query(Criteria.where("courseId").is(temp.getInt("id"))), Sessions.class);
                if(tempSession.getActive()) {
                    courseList.add(course);
                }

              //  courseList.add(course);

            }
            model.addAttribute("stackOverFlowOutPut", stackOverFlowOutPut);
            model.addAttribute("userEmail", userEmail);
            stackOverFlowOutPut.setCourses(courseList);

            System.out.println("----response1-------"+response);

            return "stackOverFlowOutPut";
        }catch(HttpClientErrorException e){

        }
		
		return "stackOverFlowOutPut";


    }
    
      
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String stackOverFlowForm(Model model, StackOverFlow stackoverflow) {
    	
    
    	
        if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) 
        {
        	return "redirect:/connect/linkedin";
        	
        }
    	
    	
				// LinkedIn Skills
				 model.addAttribute(linkedIn.profileOperations().getUserProfile());
				 /*List<LinkedInProfile> connections = linkedIn.connectionOperations().getConnections();
				 ArrayList<String> Linkedskills=(ArrayList<String>) linkedIn.profileOperations().getUserProfileFull().getSkills();
				 model.addAttribute("connections", connections);
				 model.addAttribute("Linkedskills",Linkedskills);*/
				//LinkedIn Skills
				 
				 String userEmail = linkedIn.profileOperations().getUserProfile().getEmailAddress();
				 
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
    
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String frontPage(Model model) {
     
        return "index";

    }
    
    
    @RequestMapping(value = "/sendEmail/{courseId}", method = RequestMethod.POST)
    public String sendEmail(Model model,@PathVariable("courseId") int courseId) {
     
       // System.out.println("userEmail in sendEmail::::"+userEmail);
        System.out.println("courseId in sendEmail::::"+courseId);
        
        
        if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) 
        {
        	return "redirect:connect/linkedin";
        	
        }
        
        String emailId = linkedIn.profileOperations().getUserProfile().getEmailAddress();

        
        System.out.println("userEmail in sendEmail::::" + emailId);

        try{
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
            headers.setContentType(mediaType);
            headers.set("Accept-Encoding","GZIP");
            RestTemplate restTemplate = new RestTemplate();
            System.out.println("inside call to mailer");
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            String response = restTemplate.getForObject("https://api.coursera.org/api/catalog.v1/courses?id="+courseId+"&fields=id,shortName,name,shortDescription",
                    String.class);
            System.out.println("----response1-------"+response);
            JSONObject jsonObject= new JSONObject(response);
            JSONArray listCourses= jsonObject.getJSONArray("elements");

            ArrayList<Coursera> courseList = new ArrayList();


            for(int i=0; i<listCourses.length();i++){
                JSONObject temp=listCourses.getJSONObject(i);
                Coursera course = new Coursera();

                course.setId(temp.getInt("id"));
                course.setName(temp.getString("name"));
                course.setShortName(temp.getString("shortName"));
                course.setShortdescription(temp.getString("shortDescription"));

                String url = "https://www.coursera.org/courses/"+temp.getString("shortName");

                course.setUrl(url);
                RecommendationProducer recommendationProducer= new RecommendationProducer();
                recommendationProducer.KafkaThread(course,emailId);

            }



        }catch(HttpClientErrorException e){
            e.printStackTrace();

        }
        
       // model.addAttribute("userEmail",userEmail);
        
        return "index";
        
    }

 
    
    
    
    
    

}
