package core.studyspring.singleton

import core.studyspring.beanfind.ApplicationContextExtendsFindTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class StatefulServiceTest {

    @Test
    fun statefulServiceSingleton() {
        val ac = AnnotationConfigApplicationContext(TestConfig::class.java)

        val statefulService1 = ac.getBean(StatefulService::class.java)
        val statefulService2 = ac.getBean(StatefulService::class.java)

        // ThreadA : A사용자 10000원 주문
        val userPrice = statefulService1.order("userA", 10000)
        // ThreadB : B사용자 20000원 주문
        val userPrice2 = statefulService2.order("userB", 20000)

        // ThreadA: 사용자 A 주문 금액 조회
//        val price = statefulService1.getPrice()
//        println("price $price")
        println("userPrice $userPrice")
        println("userPrice2 $userPrice2")

//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000)
    }

    @Configuration
    class TestConfig {
        companion object {
            @Bean
            fun statefulService() = StatefulService()
        }
    }
}