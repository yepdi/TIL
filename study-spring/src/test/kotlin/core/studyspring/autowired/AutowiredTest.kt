package core.studyspring.autowired

import core.studyspring.member.Member
import core.studyspring.member.MemberService
import org.jetbrains.annotations.Nullable
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import java.util.*

class AutowiredTest {

    @Test
    fun autowiredOption() {
        val ac = AnnotationConfigApplicationContext(TestBean::class.java)
    }

    class TestBean {
        companion object {
            @Autowired(required = false) // 의존 관계가 없으면 메서드 자체가 호출되지 않는다
//            @Autowired // member가 spring bean이 아니기 때문에 찾을 수 없다
            fun setNoBean1(noBean1: Member) {
                println("noBean1 = $noBean1")
            }

            @Autowired
            fun setNoBean2(@Nullable noBean1: Member) {
                println("noBean2 = $noBean1")
            }

            @Autowired
            fun setNoBean3(noBean1: Optional<Member>) {
                println("noBean3 = $noBean1")
            }
        }
    }
}