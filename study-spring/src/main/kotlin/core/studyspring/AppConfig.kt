package core.studyspring

import core.studyspring.discount.FixDiscountPolicy
import core.studyspring.discount.RateDiscountPolicy
import core.studyspring.member.MemberService
import core.studyspring.member.MemberServiceImpl
import core.studyspring.member.MemoryMemberRepository
import core.studyspring.order.OrderService
import core.studyspring.order.OrderServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    // 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다
    // 애플리케이션이 어떻게 동작할지에 대한 것을 책임진다
    // 생성한 객체 인스턴스의 참조를 생성자를 통해서 주입해준다
    // 구성 영역은 변경될 수 밖에 없다

    @Bean
    fun memberService(): MemberService {
        // 생성자 주입
        return MemberServiceImpl(memberRepository());
    }

    @Bean
    fun orderService(): OrderService {
        return OrderServiceImpl(memberRepository(), discountPolicy());
    }

    // refactoring key : command + option + m

    // 역할과 구현 클래스가 한눈에 들어온다
    @Bean
    fun discountPolicy() = RateDiscountPolicy()

    @Bean
    fun memberRepository() = MemoryMemberRepository()


}