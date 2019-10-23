package config

import java.io.FileNotFoundException
import java.io.IOException
import java.util.Properties

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory
import org.springframework.lang.Nullable

class YamlPropsFactoryBean : PropertySourceFactory {

    @Throws(IOException::class)
    override fun createPropertySource(@Nullable name: String?, resource: EncodedResource): PropertySource<*> {
        val propertiesFromYaml = loadYamlIntoProperties(resource)
        val sourceName = name?:resource.resource.filename
        return PropertiesPropertySource(sourceName!!, propertiesFromYaml!!)
    }

    @Throws(FileNotFoundException::class)
    private fun loadYamlIntoProperties(resource: EncodedResource): Properties? {
        try {
            val factory = YamlPropertiesFactoryBean()
            factory.setResources(resource.resource)
            factory.afterPropertiesSet()
            return factory.getObject()
        } catch (e: IllegalStateException) {
            // for ignoreResourceNotFound
            val cause = e.cause
            if (cause is FileNotFoundException)
                throw e.cause as FileNotFoundException
            throw e
        }

    }
}