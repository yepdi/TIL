package core.studyspring.scan.filter

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

class ComponentFilterAppConfigTest {

    @Test
    fun filterScan() {
        val ac = AnnotationConfigApplicationContext(ComponentFilterAppConfig::class.java)
        val beanA = ac.getBean("beanA", BeanA::class.java)
        assertThat(beanA).isNotNull

        assertThrows(NoSuchBeanDefinitionException::class.java) {
            ac.getBean("beanB", BeanB::class.java)
        }
    }

    @Configuration
    @ComponentScan(includeFilters = [ComponentScan.Filter(  classes = arrayOf(MyIncludeComponent::class))])
    @ComponentScan(excludeFilters = [ComponentScan.Filter(type = FilterType.ANNOTATION, classes = arrayOf(MyExcludeComponent::class))])
    class ComponentFilterAppConfig {
        companion object {

        }
    }
}