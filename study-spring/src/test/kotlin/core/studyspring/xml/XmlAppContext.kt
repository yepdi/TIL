package core.studyspring.xml

import core.studyspring.member.MemberService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericXmlApplicationContext

class XmlAppContext {

    @Test
    fun xmlAppContext() {
        val ac: ApplicationContext = GenericXmlApplicationContext("appConfig.xml")
        val memberService = ac.getBean("memberService", MemberService::class.java)
        Assertions.assertThat(memberService).isInstanceOf(MemberService::class.java)
    }
}