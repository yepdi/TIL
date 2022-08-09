package core.studyspring.singleton

import core.studyspring.AppConfig
import core.studyspring.member.MemberRepository
import core.studyspring.member.MemberServiceImpl
import core.studyspring.order.OrderServiceImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class ConfigurationSingletonTest {
    @Test
    fun configurationTest() {
        val ac = AnnotationConfigApplicationContext(AppConfig::class.java)

        val memberService = ac.getBean("memberService", MemberServiceImpl::class.java)
        val orderService = ac.getBean("orderService", OrderServiceImpl::class.java)
        val memberRepository = ac.getBean("memberRepository", MemberRepository::class.java)

        val memberRepository1 = memberService.getMemberRepository()
        val memoryRepository2 = orderService.getMemberRepository()

        println("memberRepository1 $memberRepository1")
        println("memberRepository2 $memoryRepository2")
        println("memberRepository $memberRepository")

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository)
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository)

//        memberRepository1 core.studyspring.member.MemoryMemberRepository@7db0565c
//            memberRepository2 core.studyspring.member.MemoryMemberRepository@7db0565c
//            memberRepository core.studyspring.member.MemoryMemberRepository@7db0565c

    }

    @Test
    fun configurationDeep() {
//        bean = class core.studyspring.AppConfig$$EnhancerBySpringCGLIB$$4c769945
        val ac = AnnotationConfigApplicationContext(AppConfig::class.java)
        val bean = ac.getBean(AppConfig::class.java)

        println("bean = ${bean.javaClass}")
        // @Configuration이 없으면 싱글톤이 깨진다
    }
}