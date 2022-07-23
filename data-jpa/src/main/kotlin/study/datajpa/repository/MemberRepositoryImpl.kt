package study.datajpa.repository

import study.datajpa.entity.Member
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

// MemberRepository + Impl 의 규칙을 지켜야한다
// 복잡한 동적 쿼리를 사용할 때 사용
class MemberRepositoryImpl(
    @PersistenceContext private val em: EntityManager
): MemberRepositoryCustom {

    // QueryDsl으로 custom 할 때 사용한다
    override fun findMemberCustom(): List<Member> {
        return em.createQuery("select m from Member m")
            .resultList as List<Member>
    }
}