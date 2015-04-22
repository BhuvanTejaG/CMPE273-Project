package edu.sjsu;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

/**
 * Created by vipul on 4/19/2015.
 */
@Document(collection = "Skills")
public class Skills {


    private ArrayList<String> Skill;

    public ArrayList<String> getSkill() {
        return Skill;
    }

    public void setSkill(ArrayList<String> skill) {
        Skill = skill;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Id
    private String userId;



}
