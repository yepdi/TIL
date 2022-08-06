package core.studyspring.member

class MemberServiceImpl : MemberService {
    // 의존 관계에 대한 고민은 외부에 맡기고 실행에만 집중한다
    // 담당 기능을 실행하는 책임만 진다

    // 추상화 interface 의존
    private var memberRepository: MemberRepository

    // 의존 관계를 외부에서 주입해주는 것 같다고 하여 DI(Dependency Injection) 의존관계 주입 or 의존성 주입
    constructor(memberRepository: MemberRepository) {
        this.memberRepository = memberRepository
    }

    override fun join(member: Member) {
        memberRepository.save(member)
    }

    override fun findMember(memberId: Long): Member? {
        return memberRepository.findById(memberId)
    }
}