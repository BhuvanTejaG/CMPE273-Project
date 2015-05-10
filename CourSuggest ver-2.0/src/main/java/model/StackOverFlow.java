package model;

import java.util.List;

public class StackOverFlow {

    private String skillTextBox;
    private List<String> skillList;
    private String skillDropDown;

    public List<String> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<String> skillList) {
        this.skillList = skillList;
    }

    public String getSkillDropDown() {
        return skillDropDown;
    }

    public void setSkillDropDown(String skillDropDown) {
        this.skillDropDown = skillDropDown;
    }

    public String getSkillTextBox() {
        return skillTextBox;
    }

    public void setSkillTextBox(String skillTextBox) {
        this.skillTextBox = skillTextBox;
    }

}