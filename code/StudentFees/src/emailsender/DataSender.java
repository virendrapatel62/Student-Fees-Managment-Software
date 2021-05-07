package emailsender;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class DataSender {
    
        

        public DataSender() {
            
        }

        public void sendTo(String to) throws Exception {

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
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));//u will send to
            message.setSubject("Student Fees Managment Database");
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            //attached 1 --------------------------------------------
            String file = "database.db";
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(file);
            multipart.addBodyPart(messageBodyPart);
//    //------------------------------------------------------    
/* //     //attached 2 --------------------------------------------  
            String file2 = "batch.xlsx";
            String fileName2 = "batch.xlsx";
            messageBodyPart = new MimeBodyPart();
            DataSource source2 = new FileDataSource(file2);
            messageBodyPart.setDataHandler(new DataHandler(source2));
            messageBodyPart.setFileName(fileName2);
            multipart.addBodyPart(messageBodyPart);
*/
//attached 3------------------------------------------------
            message.setContent(multipart);
            Transport.send(message);
        }

}
