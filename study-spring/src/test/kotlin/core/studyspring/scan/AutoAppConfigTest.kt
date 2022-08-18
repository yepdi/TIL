package core.studyspring.scan

import core.studyspring.AutoAppConfig
import core.studyspring.member.MemberService
import core.studyspring.order.OrderServiceImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class AutoAppConfigTest {

    @Test
    fun basicScan() {
        val ac = AnnotationConfigApplicationContext(AutoAppConfig::class.java)

        val memberService = ac.getBean(MemberService::class.java)

        Assertions.assertThat(memberService).isInstanceOf(MemberService::class.java)
        val bean = ac.getBean(OrderServiceImpl::class.java)
        val memberRepository = bean.getMemberRepository()
        println("memberRepository $memberRepository")

//       ClassPathBeanDefinitionScanner - Identified candidate component class: file [/Users/user/study/study-spring/build/classes/kotlin/main/core/studyspring/discount/RateDiscountPolicy.class]
//       ClassPathBeanDefinitionScanner - Identified candidate component class: file [/Users/user/study/study-spring/build/classes/kotlin/main/core/studyspring/member/MemberServiceImpl.class]
//       ClassPathBeanDefinitionScanner - Identified candidate component class: file [/Users/user/study/study-spring/build/classes/kotlin/main/core/studyspring/member/MemoryMemberRepository.class]
//       ClassPathBeanDefinitionScanner - Identified candidate component class: file [/Users/user/study/study-spring/build/classes/kotlin/main/core/studyspring/order/OrderServiceImpl.class]

//       DefaultListableBeanFactory - Creating shared instance of singleton bean 'orderServiceImpl'
//       DefaultListableBeanFactory - Autowiring by type from bean name 'orderServiceImpl' via constructor to bean named 'memoryMemberRepository'
    }

}