package core.studyspring

import core.studyspring.member.Grade
import core.studyspring.member.Member
import core.studyspring.member.MemberService
import core.studyspring.member.MemberServiceImpl
import core.studyspring.order.OrderService
import core.studyspring.order.OrderServiceImpl
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class OrderApp {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
//            val appConfig = AppConfig()
//            val memberService = appConfig.memberService()
//            val orderService = appConfig.orderService()

            val applicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)
            val memberService = applicationContext.getBean("memberService", MemberService::class.java)
            val orderService = applicationContext.getBean("orderService", OrderService::class.java)

            val memberId = 1L
            val member = Member(memberId, "memberA", Grade.VIP)
            memberService.join(member)

            val order = orderService.createOrder(memberId, "IteamA", 10000)
            println(order)
            println(order.calculatePrice())
        }
    }
}