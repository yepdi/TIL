package core.studyspring.lifecycle

import org.junit.jupiter.api.Test
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

class BeanLifeCycleTest {

    @Test
    fun lifeCycleTest() {
        val applicationContext : ConfigurableApplicationContext = AnnotationConfigApplicationContext(LifeCycleConfig::class.java)
        val client = applicationContext.getBean(NetworkClient::class.java)

        applicationContext.close()
        // close는 기본 ApplicationContext 내 정의가 되어 있지 않다

//        생성자 호출 url = null
//        connect : null
//        call null message 초기화 연결 메시지
    }

    @Configuration
    class LifeCycleConfig {
        companion object {
//            @Bean(initMethod = "init", destroyMethod = "close")
            @Bean
            fun networkClient(): NetworkClient {
                val networkClient = NetworkClient()
                // 객체 생성 후 url 을 설정해주었다
                networkClient.setUrl("http://hello-spring.dev")
                return networkClient
            }
        }
    }
}