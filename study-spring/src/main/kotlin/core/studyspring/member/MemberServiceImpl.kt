package core.studyspring.member

class MemberServiceImpl : MemberService {

    // 구현체 의존 DIP 위반
    private val memberRepository: MemberRepository = MemoryMemberRepository()

    override fun join(member: Member) {
        memberRepository.save(member)
    }

    override fun findMember(memberId: Long): Member? {
        return memberRepository.findById(memberId)
    }
}