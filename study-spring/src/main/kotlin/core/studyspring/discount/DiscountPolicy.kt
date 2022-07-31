package core.studyspring.discount

import core.studyspring.member.Member

interface DiscountPolicy {
    /**
     * @return 할인 대상 금액
     */
    fun discount(member: Member, price: Int): Int
}