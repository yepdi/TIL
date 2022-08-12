package core.studyspring.member

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class MemoryMemberRepository : MemberRepository {

    companion object {
        val store = HashMap<Long, Member>()
        // 동시성 이슈인 경우에는 ConcurrentHashMap 사용
    }

    override fun save(member: Member) {
        store[member.id] = member
    }

    override fun findById(memberId: Long): Member? {
        return store[memberId]
    }
}