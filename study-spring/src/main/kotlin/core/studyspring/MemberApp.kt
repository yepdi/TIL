package core.studyspring

import core.studyspring.member.Grade
import core.studyspring.member.Member
import core.studyspring.member.MemberServiceImpl

class MemberApp {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val memberService = MemberServiceImpl()
            val member = Member(1L, "memberA", Grade.VIP)
            memberService.join(member)

            val findMember = memberService.findMember(1L)
            println("new member = $findMember")
            println("member = $member")
        }
    }
}