package core.studyspring.scope

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Scope
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

class SingletonTest {

    @Test
    fun singletonBeanFind() {
        val ac = AnnotationConfigApplicationContext(SingletonBean::class.java)
        val singletonBean = ac.getBean(SingletonBean::class.java)
        val singletonBean1 = ac.getBean(SingletonBean::class.java)

        println("singletonBean $singletonBean")
        println("singletonBean1 $singletonBean1")

        Assertions.assertThat(singletonBean1).isSameAs(singletonBean)

        ac.close()
    }

    @Scope("singleton")
    class SingletonBean {
        @PostConstruct
        fun init() {
            println("SingletonBean.init")
        }

        @PreDestroy
        fun destroy() {
            println("SingletonBean.destroy")
        }
    }
}