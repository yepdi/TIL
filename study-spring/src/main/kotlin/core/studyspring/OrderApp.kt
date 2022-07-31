package core.studyspring

import core.studyspring.member.Grade
import core.studyspring.member.Member
import core.studyspring.member.MemberServiceImpl
import core.studyspring.order.OrderServiceImpl

class OrderApp {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val memberService = MemberServiceImpl()
            val orderService = OrderServiceImpl()

            val memberId = 1L
            val member = Member(memberId, "memberA", Grade.VIP)
            memberService.join(member)

            val order = orderService.createOrder(memberId, "IteamA", 10000)
            println(order)
            println(order.calculatePrice())
        }
    }
}