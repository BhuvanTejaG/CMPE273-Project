package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StackOverFlowParentModel {

    public ArrayList<HashMap<String, String>> getItems() {
        return items;
    }

    public void setItems(ArrayList<HashMap<String, String>> items) {
        this.items = items;
    }

    @JsonProperty("items")
    private ArrayList<HashMap<String, String>> items;

    private  String has_more;
    private String quota_max;

    public String getHas_more() {
        return has_more;
    }

    public void setHas_more(String has_more) {
        this.has_more = has_more;
    }

    public String getQuota_max() {
        return quota_max;
    }

    public void setQuota_max(String quota_max) {
        this.quota_max = quota_max;
    }

    public String getQuota_remaining() {
        return quota_remaining;
    }

    public void setQuota_remaining(String quota_remaining) {
        this.quota_remaining = quota_remaining;
    }

    private String quota_remaining;

}