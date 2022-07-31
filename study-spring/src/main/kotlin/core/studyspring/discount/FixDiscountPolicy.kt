package core.studyspring.discount

import core.studyspring.member.Grade
import core.studyspring.member.Member

class FixDiscountPolicy: DiscountPolicy {

    private val discountFixAmount = 1000

    override fun discount(member: Member, price: Int): Int {
        return if (member.grade == Grade.VIP) {
            discountFixAmount
        } else {
            0
        }
    }
}