package core.studyspring

import core.studyspring.member.MemoryMemberRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

// 컴포넌트 스캔을 사용하려면 @ComponentScan 을 설정 정복에 붙여주면 된다
@Configuration
@ComponentScan(
    //@Configuration 도 자동으로 등록해준다
    basePackages = ["core.studyspring"],
//    basePackageClasses = [AutoAppConfig::class.java],
    excludeFilters = [ComponentScan.Filter(type = FilterType.ANNOTATION, classes = arrayOf(Configuration::class))]
)
class AutoAppConfig {

//    @Bean(name = ["memoryMemberRepository"])
//    fun memberRepository() = MemoryMemberRepository()
}