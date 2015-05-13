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
   // Int - Session Id

    @Id
    private int id;

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
/*
    public String getHomeLink() {
        return homeLink;
    }

    public void setHomeLink(String homeLink) {
        this.homeLink = homeLink;
    }
*/
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
/*
    public String getDurationString() {
        return durationString;
    }

    public void setDurationString(String durationString) {
        this.durationString = durationString;
    }*/
/*
    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }
*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*
    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }
*/
    // Int - Course Id
    private int courseId;

  /*  //String - Home link for the course.
    private String homeLink;
*/
    //Int - Session status.
    private int status;

    //Boolean - Whether the session is active.
    private Boolean active;
/*
    //String - Approximate (human readable) duration string. Typically of the format ‘XX weeks’.
    private String durationString;*/
/*
    //Int - Optional start day.
    private int startDay;

    //Int - Optional start month.
    private int startMonth;

    //Int - Optional start year.
    private int startYear;
*/
    //String - Session iteration name.
    private String name;

    /*        signatureTrackCloseTime: Option[DateTime] - Signature track information.
            signatureTrackOpenTime: Option[DateTime] - Signature track information.
            signatureTrackPrice: Option[Double] - Signature track information.
            signatureTrackRegularPrice: Option[Float] - Signature track information.
            eligibleForCertificates: Option[Boolean] - Certificates allowed.
    eligibleForSignatureTrack: Option[Boolean] - Signature track available.
            certificateDescription: Option[String] - Description on the course certificate.
            certificatesReady: Boolean - Whether the certificates have been released.
*/
}
