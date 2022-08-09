package core.studyspring.singleton

import core.studyspring.AppConfig
import core.studyspring.member.MemberService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    fun pureContainer() {
        val appConfig = AppConfig()
        //1. 조회: 호출할 때 마다 객체를 생성
        val memberService1 = appConfig.memberService()

        //2. 조회: 호출할 때 마다 객체를 생성
        val memberService2 = appConfig.memberService()

        // 참조값 다른 것 확인
        // memberService1 core.studyspring.member.MemberServiceImpl@687e99d8
        // memberService2 core.studyspring.member.MemberServiceImpl@38102d01
        println("memberService1 ${memberService1}")
        println("memberService2 ${memberService2}")

        // memberService1 != memberService2
        Assertions.assertThat(memberService1).isNotEqualTo(memberService2)
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    fun singletonServiceTest() {
        val singletonService1 = SingletonService.getInstance()
        val singletonService2 = SingletonService.getInstance()

        println("singletonService1 ${singletonService1}")
        println("singletonService2 ${singletonService2}")

        Assertions.assertThat(singletonService1).isSameAs(singletonService2)
        // same 객체 instance 비교
        // equal java Equals 비교
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    fun springContainer() {
        val ac = AnnotationConfigApplicationContext(AppConfig::class.java)
        //1. 조회: 호출할 때 마다 객체를 생성
        val memberService1 = ac.getBean("memberService", MemberService::class.java)

        //2. 조회: 호출할 때 마다 객체를 생성
        val memberService2 = ac.getBean("memberService", MemberService::class.java)

//        // 참조값 다른 것 확인
//      memberService1 core.studyspring.member.MemberServiceImpl@4d9d1b69
//      memberService2 core.studyspring.member.MemberServiceImpl@4d9d1b69
        println("memberService1 ${memberService1}")
        println("memberService2 ${memberService2}")

        // memberService1 != memberService2
        Assertions.assertThat(memberService1).isSameAs(memberService2)
    }
}