package Consumer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mailer {

    private static String USER_NAME = "coursuggest";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "admin123!@#"; // GMail password
    private static String RECIPIENT = "cloudwatchalarm@gmail.com";

	//testing mail functionality
    public static void main(String[] args) {
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { RECIPIENT }; // list of recipient email addresses
        String subject = "Java send mail example";
        String body = "Welcome to JavaMail!";

       sendFromGMail(from, pass, to, subject, body);
    }

    public void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
		//stub for sending mail
    }
}