package business;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * {@code MailUtility} is a simple class that provides a {@code static method} for sending emails.
 *
 * @see #sendMail(String, String, String)
 * @version 1.0
 */
public class MailUtility {

    /**
     * Sends an email to the desired receiver, provided its mail address, the subject and the body of the message
     *
     * @param receiverMail the mail of the receiver
     * @param messageSubject The subject of the mail
     * @param messageBody The body of the mail
     * @throws AddressException Whenever the receiverMail provided is not well-formatted
     */
    public static void sendMail(String receiverMail, String messageSubject, String messageBody) throws AddressException {
        // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
        String remitente = "dpobassols";  //Para la dirección nomcuenta@gmail.com

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", "111222333a!");    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
        Address dest=new InternetAddress(receiverMail);
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, dest);   //Se podrían añadir varios de la misma manera
            message.setSubject(messageSubject);
            message.setText(messageBody);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, "111222333a!");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
        }
    }

}


