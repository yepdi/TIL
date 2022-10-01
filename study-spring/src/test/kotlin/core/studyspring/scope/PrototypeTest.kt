package core.studyspring.scope

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

class PrototypeTest {

    @Test
    fun prototypeBeanFind() {
        val ac = AnnotationConfigApplicationContext(PrototypeBean::class.java)
        println("find prototypeBean")
        val prototypeBean = ac.getBean(PrototypeBean::class.java)
        println("find prototypeBean1")
        val prototypeBean1 = ac.getBean(PrototypeBean::class.java)

        println("prototypeBean $prototypeBean")
        println("prototypeBean1 $prototypeBean1")

        Assertions.assertThat(prototypeBean).isNotSameAs(prototypeBean1)

        ac.close()
        // 종료 메서드 호출도 클라이언트가 직접 해야한다
    }

    @Scope("prototype")
    // AnnotationConfigApplicationContext 내 선언했기 때문에 Bean으로 등록이 된다
    class PrototypeBean {
        @PostConstruct
        fun init() {
            println("PrototypeBean.init")
        }

        @PreDestroy
        fun destroy() {
            println("PrototypeBean.destroy")
        }
    }
}