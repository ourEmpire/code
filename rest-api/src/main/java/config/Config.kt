package config

import jdk.nashorn.internal.objects.annotations.Getter
import jdk.nashorn.internal.objects.annotations.Setter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource(factory=YamlPropsFactoryBean::class,value= ["classpath:start.yml"])
@ConfigurationProperties(prefix="config")
class Config {
    var url: String? = null
    var dir: String? = null
    var name: String? = null
}


@Component
@PropertySource(factory=YamlPropsFactoryBean::class,value= ["classpath:start.yml"])
@ConfigurationProperties(prefix="mail")
class MailConf {
    var conf: HashMap<String,Any>? = null
}

