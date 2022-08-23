package core.studyspring.autowired

import core.studyspring.AutoAppConfig
import core.studyspring.discount.DiscountPolicy
import core.studyspring.member.Grade
import core.studyspring.member.Member
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class AllBeanTest {

    // 전략 패턴 처럼 사용

    @Test
    fun findAllBean() {
        // 다형성 코드
        val ac = AnnotationConfigApplicationContext(AutoAppConfig::class.java, DiscountService::class.java)

        val discountService = ac.getBean(DiscountService::class.java)

        val member = Member(1L, "userA", Grade.VIP)
        val discount = discountService.discount(member, 10000, "fixDiscountPolicy")

        Assertions.assertThat(discountService).isInstanceOf(DiscountService::class.java)
        Assertions.assertThat(discount).isEqualTo(1000)

        val rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy")
        Assertions.assertThat(rateDiscountPrice).isEqualTo(2000)

    }

    class DiscountService {
        fun discount(member: Member, price: Int, discountCode: String): Int {
            val discountPolicy = policyMap.get(discountCode)
            return discountPolicy?.discount(member, price)!!
        }

        var policyMap: Map<String, DiscountPolicy>
        var policies : List<DiscountPolicy>

        @Autowired
        constructor(policyMap: Map<String, DiscountPolicy>, policies : List<DiscountPolicy>) {
            this.policies = policies
            this.policyMap = policyMap
            println("policies $policies")
            println("policyMap $policyMap")
        }
    }
}