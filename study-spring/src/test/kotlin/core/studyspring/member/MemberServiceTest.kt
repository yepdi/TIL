package core.studyspring.member

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class MemberServiceTest {

    val memberService = MemberServiceImpl()

    @Test
    fun join() {
        // given
        val member = Member(1L, "memberA", Grade.VIP)

        // when
        memberService.join(member)
        val findMember = memberService.findMember(1L)

        // then
        Assertions.assertThat(member).isEqualTo(findMember)
    }
}