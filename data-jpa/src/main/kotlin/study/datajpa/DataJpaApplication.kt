package study.datajpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@SpringBootApplication
//@EnableJpaRepositories(basePackages = "study.datajpa.repository") -> spring boot를 사용하면 없어도 된다
class DataJpaApplication {
	@Bean
	fun auditorProvider(): AuditorAware<String> {
		return AuditorAware { Optional.of(UUID.randomUUID().toString()) }
	}
}

// 참조 링크 : https://itecnote.com/tecnote/spring-boot-spring-boot-data-jpa-createdby-and-updatedby-not-populating-with-authenticating-with-oidc/

fun main(args: Array<String>) {
	runApplication<DataJpaApplication>(*args)
}
