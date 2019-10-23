package config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@PropertySource(factory=YamlPropsFactoryBean.class,value= {"classpath:mail.yml"})
@ConfigurationProperties(prefix="email")
public class MailConfig {
    private HashMap<String,Object> conf;
    private HashMap<String,Object> smtp;

    public HashMap<String, Object> getConf() {
        return conf;
    }

    public void setConf(HashMap<String, Object> conf) {
        this.conf = conf;
    }

    public HashMap<String, Object> getSmtp() {
        return smtp;
    }

    public void setSmtp(HashMap<String, Object> smtp) {
        this.smtp = smtp;
    }
}
