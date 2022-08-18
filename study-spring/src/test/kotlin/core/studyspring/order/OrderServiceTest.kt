package core.studyspring.order

import core.studyspring.AppConfig
import core.studyspring.member.Grade
import core.studyspring.member.Member
import core.studyspring.member.MemberService
import core.studyspring.member.MemberServiceImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrderServiceTest {
    lateinit var orderService: OrderService
    lateinit var memberService: MemberService

    @BeforeEach
    fun beforeEach() {
        val appConfig = AppConfig()
        memberService = appConfig.memberService()
        orderService = appConfig.orderService()
    }
    @Test
    fun createOrder() {
        val memberId = 1L
        val member = Member(memberId, "memberA", Grade.VIP)
        memberService.join(member)

        val order = orderService.createOrder(memberId, "IteamA", 10000)
        Assertions.assertThat(order.discountPrice).isEqualTo(1000)
    }

//    @Test
//    fun fieldInjectionTest() {
//        val orderService = OrderServiceImpl()
//        orderService.createOrder(1L, "itemA", 10000)
//    }

}