package core.studyspring.discount

import core.studyspring.member.Grade
import core.studyspring.member.Member
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class RateDiscountPolicyTest {
    val discountPolicy = RateDiscountPolicy()

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다")
    fun vip_o() {
        // given
        val member = Member(1L, "memberVIP", Grade.VIP)
        // when
        val discount = discountPolicy.discount(member, 10000)
        // then
        assertThat(discount).isEqualTo(1000)
    }

    @Test
    @DisplayName("VIP가 아니면 할인이 적용되지 않아야한다")
    fun vip_x() {
        // given
        val member = Member(2L, "memberBASIC", Grade.BASIC)
        // when
        val discount = discountPolicy.discount(member, 10000)
        // then
        assertThat(discount).isEqualTo(0)
    }
}