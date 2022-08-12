package core.studyspring.discount

import core.studyspring.member.Grade
import core.studyspring.member.Member
import org.springframework.stereotype.Component

@Component
class RateDiscountPolicy: DiscountPolicy {

    private val discountPercent: Int = 10

    override fun discount(member: Member, price: Int): Int {
        if (member.grade == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}