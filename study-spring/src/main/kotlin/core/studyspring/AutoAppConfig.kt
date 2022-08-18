package core.studyspring

import core.studyspring.discount.DiscountPolicy
import core.studyspring.member.MemberRepository
import core.studyspring.member.MemoryMemberRepository
import core.studyspring.order.OrderService
import core.studyspring.order.OrderServiceImpl
import org.springframework.beans.factory.annotation.Autowired
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

    // 필드 injection 사용 예시
//    @Autowired lateinit var memberRepository: MemberRepository
//    @Autowired lateinit var discountPolicy: DiscountPolicy
//
//    @Bean
//    fun orderService(): OrderService {
//        return OrderServiceImpl(memberRepository, discountPolicy)
//    }
}