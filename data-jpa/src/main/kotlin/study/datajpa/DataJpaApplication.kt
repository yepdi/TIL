package study.datajpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "study.datajpa.repository") -> spring boot를 사용하면 없어도 된다
class DataJpaApplication

fun main(args: Array<String>) {
	runApplication<DataJpaApplication>(*args)
}
