
package emailsender;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    public static void sendTo(String to , String content , String subject) throws Exception {

            final String username = "StudentFeeBook@gmail.com"; //ur email
            final String password = "patelshb";
            Properties props = new Properties();
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", true);
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("StudentFeeBook@gmail.com"));//ur email
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));//u will send to
            message.setSubject(subject);
            message.setContent(content, "text/html");

            Transport.send(message);
        }
}
