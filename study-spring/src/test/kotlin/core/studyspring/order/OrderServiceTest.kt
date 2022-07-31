package core.studyspring.order

import core.studyspring.member.Grade
import core.studyspring.member.Member
import core.studyspring.member.MemberServiceImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class OrderServiceTest {
    val memberService = MemberServiceImpl()
    val orderService = OrderServiceImpl()

    @Test
    fun createOrder() {
        val memberId = 1L
        val member = Member(memberId, "memberA", Grade.VIP)
        memberService.join(member)

        val order = orderService.createOrder(memberId, "IteamA", 10000)
        Assertions.assertThat(order.discountPrice).isEqualTo(1000)
    }
}