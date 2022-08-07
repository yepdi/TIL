package core.studyspring.beandefinition

import core.studyspring.AppConfig
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.GenericXmlApplicationContext

class BeanDefinitionTest {
//    val ac = AnnotationConfigApplicationContext(AppConfig::class.java) factoryBeanName, factoryMethodName 존재
    // beanDefitionName = memberRepository beanDefinition = Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false;
    // factoryBeanName=appConfig; factoryMethodName=memberRepository; initMethodName=null; destroyMethodName=(inferred); defined in core.studyspring.AppConfig

    val ac = GenericXmlApplicationContext("appConfig.xml")
    // ApplicationContext로 하면 getBeanDefinition을 불러 올 수가 없다
    // beanDefitionName = discountPolicy beanDefinition = Generic bean: class [core.studyspring.discount.RateDiscountPolicy]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false;
    // factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in class path resource [appConfig.xml]

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    fun findApplicationBean() {
        val beanDefinitionNames = ac.beanDefinitionNames
        for (beanDefinitionName in beanDefinitionNames) {
            val beanDefinition = ac.getBeanDefinition(beanDefinitionName)

            if (beanDefinition.role == BeanDefinition.ROLE_APPLICATION) {
                println("beanDefitionName = $beanDefinitionName beanDefinition = $beanDefinition")
            }
        }
    }
}