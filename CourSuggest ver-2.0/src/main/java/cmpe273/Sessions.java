package cmpe273;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by vipul on 5/10/2015.
 */
@JsonIgnoreProperties
@Document(collection = "Sessions")
public class Sessions {

    @Id
    private int id;
    
    // Int - Course Id
    private int courseId;
    
    //Int - Session status.
    private int status;

    //Boolean - Whether the session is active.
    private Boolean active;
    
    //String - Session iteration name.
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
