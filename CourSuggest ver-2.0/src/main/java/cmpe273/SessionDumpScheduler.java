package cmpe273;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Coursera;
import model.StackOverFlow;
import model.StackOverFlowOutPut;
import model.StackOverFlowParentModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by vipul on 5/10/2015.
 */
@Component
public class SessionDumpScheduler {
    ApplicationContext context= new AnnotationConfigApplicationContext(SpringMongoConfig.class);
    MongoOperations mongoOperations= (MongoOperations)context.getBean("mongoTemplate");

    @Scheduled(fixedRate = 3000)
    public void sessionDump(){

        JSONArray listCourses=sessionsData();
        if(listCourses!=null) {
            for (int i = 0; i < listCourses.length(); i++) {
                JSONObject temp = listCourses.getJSONObject(i);
                Sessions session = new Sessions();


                session.setId(temp.getInt("id"));
                session.setActive(temp.getBoolean("active"));
                session.setCourseId(temp.getInt("courseId"));
                session.setStatus(temp.getInt("status"));
               /* session.setName(temp.getString("name"));
                session.setHomeLink(temp.getString("homeLink"));
                session.setDurationString(temp.getString("durationString"));*/
               /*
                session.setStartDay(temp.getInt("startDay"));
                session.setStartMonth(temp.getInt("startMonth"));
                session.setStartYear(temp.getInt("startYear"));*/
                mongoOperations.save(session);

            }


        }
    }

    public JSONArray sessionsData()  {

        JSONArray listCourses=null;
       try{
            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));
            headers.setContentType(mediaType);
            //headers.set("Accept-Encoding","GZIP");
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            String response = restTemplate.getForObject("https://api.coursera.org/api/catalog.v1/sessions?fields=id,courseId,homeLink,status,active,durationString,startDay,startMonth,startYear,name",String.class);
            System.out.println("----sessions-------"+response);
            JSONObject jsonObject= new JSONObject(response);
            listCourses= jsonObject.getJSONArray("elements");

           /* ArrayList<Coursera> courseList = new ArrayList();



            model.addAttribute("stackOverFlowOutPut", stackOverFlowOutPut);
            stackOverFlowOutPut.setCourses(courseList);

            System.out.println("----response1-------"+response);*/



            return listCourses;
        }catch(HttpClientErrorException e){

        }
        return listCourses;


     }

   }
