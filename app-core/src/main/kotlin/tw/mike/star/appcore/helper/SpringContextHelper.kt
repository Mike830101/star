package tw.mike.star.appcore.helper

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

/**
 * Spring ApplicationContextAware 物件
 */
@Component
object SpringContextHelper : ApplicationContextAware {

    private lateinit var applicationContext: ApplicationContext

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }



    /**
     * Get Spring bean by name
     * @param beanName bean name
     */
    fun getBean(beanName: String): Any {
        return applicationContext.getBean(beanName)
    }

    /**
     * Get Spring bean by name and type
     *
     * @param beanName bean name
     * @param clazz bean class
     */
    fun <T> getBean(beanName: String, clazz: Class<T>): T {
        return applicationContext.getBean(beanName, clazz)
    }

    /**
     * Get Spring bean by type
     *
     * @param clazz bean class
     */
    fun <T> getBean(clazz: Class<T>): T {
        return applicationContext.getBean(clazz)
    }

    /**
     * Get environment property
     *
     * @param propertyKey property key
     * @param clazz property class
     * @param defaultValue default value
     * @param <T> property type
    </T> */
    fun <T: Any> getValue(propertyKey: String, clazz: Class<T>, defaultValue: T): T {
        return applicationContext.environment.getProperty(propertyKey, clazz, defaultValue)
    }

    /**
     * Get environment property, string value.
     *
     * @param propertyKey property key
     * @param defaultValue default value
     */
    fun stringValue(propertyKey: String, defaultValue: String): String {
        return getValue(
            propertyKey,
            String::class.java, defaultValue
        )
    }
}

