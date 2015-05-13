package model;




public class Coursera {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String name;
    private String shortName;
    private String shortdescription;
    private int id;
    private String url;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getShortName() {
        return shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public String getShortdescription() {
        return shortdescription;
    }
    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
