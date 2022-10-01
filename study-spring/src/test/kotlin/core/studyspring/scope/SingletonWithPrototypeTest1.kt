package core.studyspring.scope

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Scope
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.inject.Provider

class SingletonWithPrototypeTest1 {

    @Test
    fun prototyepFind() {
        val ac = AnnotationConfigApplicationContext(PrototypeBean::class.java)
        val prototypeBean = ac.getBean(PrototypeBean::class.java)
        prototypeBean.addCount()

        Assertions.assertThat(prototypeBean.getCount()).isEqualTo(1)

        val prototypeBean2 = ac.getBean(PrototypeBean::class.java)
        prototypeBean2.addCount()

        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1)
    }

    @Test
    fun singletonClientUsePrototype() {
        val ac = AnnotationConfigApplicationContext(ClientBean::class.java, PrototypeBean::class.java)

        val clientBean = ac.getBean(ClientBean::class.java)
        val count = clientBean.logic()

        Assertions.assertThat(count).isEqualTo(1)

        val clientBean1 = ac.getBean(ClientBean::class.java)
        val count1 = clientBean1.logic()

        Assertions.assertThat(count1).isEqualTo(1)
    }

    @Scope("singleton")
//    class ClientBean @Autowired constructor(private var prototypeBean: PrototypeBean) {
    class ClientBean {

        @Autowired
        lateinit var prototypeBeanProvider: Provider<PrototypeBean>

//    @Autowired
//    lateinit var applicationContext: ApplicationContext

        // spring container 가 Prototype을 생성시점에 주입한다
        fun logic(): Int {
            // 찾아주는 기능 제공
            val prototypeBean = prototypeBeanProvider.get()
//            val prototypeBean = applicationContext.getBean(PrototypeBean::class.java)
            prototypeBean.addCount()
            return prototypeBean.getCount()
        }
    }

    @Scope("prototype")
    class PrototypeBean {
        private var count = 0

        fun addCount() {
            this.count++
        }

        fun getCount(): Int {
            return this.count
        }

        @PostConstruct
        fun init() {
            println("PrototypeBean.init $this")
        }

        @PreDestroy
        fun destroy() {
            println("PrototypeBean.destroy")
        }
    }
}