package cmpe273;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by vipul on 5/10/2015.
 */
@Document(collection = "Courses")
public class Courses {

    @Id
    private int id;

    private String shortName;

    private String name;

    private String language;

    private String largeIcon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLargeIcon() {
        return largeIcon;
    }

    public void setLargeIcon(String largeIcon) {
        this.largeIcon = largeIcon;
    }

    public String getSubtitleLanguagesCsv() {
        return subtitleLanguagesCsv;
    }

    public void setSubtitleLanguagesCsv(String subtitleLanguagesCsv) {
        this.subtitleLanguagesCsv = subtitleLanguagesCsv;
    }

    public boolean getIsTranslate() {
        return isTranslate;
    }

    public void setIsTranslate(boolean isTranslate) {
        this.isTranslate = isTranslate;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    private String subtitleLanguagesCsv;

    private boolean isTranslate;

    private String shortDescription;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;




}
