package core.studyspring.order

import core.studyspring.discount.FixDiscountPolicy
import core.studyspring.member.Grade
import core.studyspring.member.Member
import core.studyspring.member.MemoryMemberRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class OrderServiceImplTest {

    @Test
    fun createOrder() {
        val memberRepository = MemoryMemberRepository()
        memberRepository.save(Member(1L, "name", Grade.VIP))
        val orderService = OrderServiceImpl(memberRepository, FixDiscountPolicy())
        // 의존관계 주입 : NPE 발생 의존관계가 눈에 보이지 않는다
        // 생성자 주입 이면 compile error 발생
        val order = orderService.createOrder(1L, "itemA", 1000)
        Assertions.assertThat(order.discountPrice).isEqualTo(1000)
    }
}