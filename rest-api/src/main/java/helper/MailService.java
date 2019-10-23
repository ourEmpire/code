package helper;

import config.MailConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 *  from        发件人
 *  to          收件人
 *  password    发件人第三方软件发送授权码
 *  subject     邮件主题
 *  content     邮件内容
 */
@RestController
public class MailService {
    private static String from = "caretine@qq.com";
    private static String subject = "验证码";
    private static String defaultEncoding = "UTF-8";
    private static String type = "text/html;charset=UTF-8";
    private static String password = "pwohoucpookpfgcg";
    private static String host = "smtp.qq.com";
    private static boolean auth = false;
    private static String protocol = "smtp";
    private String content;
    public boolean sendSimpleMail(String to,int verify) {
        try {
            setContent(verify);
            Properties props = new Properties();
            props.setProperty("mail.smtp.auth",String.valueOf(auth));
            props.setProperty("mail.smtp.host",host);
            props.setProperty("mail.transport.protocol",protocol);
            Session session = Session.getInstance(props);
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(to));
            msg.setSubject(subject,defaultEncoding);
            msg.setContent(content,type);
            Transport transport = session.getTransport();
            transport.connect(from,password);
            transport.sendMessage(msg,msg.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //设置发送内容
    private void setContent(int verify){
        this.content = "<html><body>不要告诉任何人！验证码为<b>" + verify
                +"</b>欢迎您注册本网站，任何人索取验证码都是诈骗。</body></html>";
    }



}
