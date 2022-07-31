package core.studyspring.order

import core.studyspring.discount.FixDiscountPolicy
import core.studyspring.member.MemoryMemberRepository

class OrderServiceImpl: OrderService {
    private val memberRepository = MemoryMemberRepository()
    private val discountPolicy = FixDiscountPolicy()

    override fun createOrder(memberId: Long, itemName: String, itemPrice: Int): Order {
        // 단일 책임 원칙
        val member = memberRepository.findById(memberId)
        val discountPrice = discountPolicy.discount(member!!, itemPrice)
        return Order(memberId, itemName, itemPrice, discountPrice)
    }
}