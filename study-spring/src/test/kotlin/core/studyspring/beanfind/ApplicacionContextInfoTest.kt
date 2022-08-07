package core.studyspring.beanfind

import core.studyspring.AppConfig
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class ApplicacionContextInfoTest {

    val ac: AnnotationConfigApplicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)


    @Test
    @DisplayName("모든 빈 출력하기")
    fun findAllBean() {
        val beanDefinitionNames = ac.beanDefinitionNames
        for (beanDefinitionName in beanDefinitionNames) {
            val b = ac.getBean(beanDefinitionName)
            println("name $beanDefinitionName object $b")
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    fun findApplicationBean() {
        val beanDefinitionNames = ac.beanDefinitionNames
        for (beanDefinitionName in beanDefinitionNames) {
            val beanDefinition = ac.getBeanDefinition(beanDefinitionName)

            // Role ROLE_APPLICATION : 직접 등록한 애플리케이션 빈
            // Role ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈
            if (beanDefinition.role == BeanDefinition.ROLE_APPLICATION) {
                val b = ac.getBean(beanDefinitionName)
                println("name $beanDefinitionName object $b")
            }
        }
    }
}