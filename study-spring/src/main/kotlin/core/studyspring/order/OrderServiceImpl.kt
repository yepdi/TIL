package core.studyspring.order

import core.studyspring.discount.DiscountPolicy
import core.studyspring.discount.FixDiscountPolicy
import core.studyspring.discount.RateDiscountPolicy
import core.studyspring.member.MemberRepository
import core.studyspring.member.MemoryMemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrderServiceImpl: OrderService {
    private var memberRepository: MemberRepository
    // 할인 정책을 변경하려면 클라이언트인 OrderServiceImpl 코드를 고쳐야한다
    // 구체(구현) 클래스에 의존하고 있다
    // DIP (구체 클래스가 아닌 추상 클래스에 의존하라) 위반, OCP 위반 (기능을 확장해서 변경하면 클라이언트 코드에 영항)
//    private val discountPolicy = RateDiscountPolicy()
    private var discountPolicy: DiscountPolicy
    // 이렇게 하면 NPE 발생. OrderServiceImpl에 DiscountPolicy를 할당해줘야한다

    @Autowired
    constructor(memberRepository: MemberRepository, discountPolicy: DiscountPolicy) {
        this.memberRepository = memberRepository
        this.discountPolicy = discountPolicy
    }

    override fun createOrder(memberId: Long, itemName: String, itemPrice: Int): Order {
        // 단일 책임 원칙
        val member = memberRepository.findById(memberId)
        val discountPrice = discountPolicy.discount(member!!, itemPrice)
        return Order(memberId, itemName, itemPrice, discountPrice)
    }

    // 테스트 용도
    fun getMemberRepository() = memberRepository
}