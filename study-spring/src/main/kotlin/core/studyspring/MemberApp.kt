package core.studyspring

import core.studyspring.member.Grade
import core.studyspring.member.Member
import core.studyspring.member.MemberService
import core.studyspring.member.MemberServiceImpl
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class MemberApp {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
//            val appConfig = AppConfig()
//            val memberService = appConfig.memberService()
//            val memberService = MemberServiceImpl()
            val applicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)
            val memberService = applicationContext.getBean("memberService", MemberService::class.java)
            val member = Member(1L, "memberA", Grade.VIP)
            memberService.join(member)

            val findMember = memberService.findMember(1L)
            println("new member = $findMember")
            println("member = $member")
        }
    }
}