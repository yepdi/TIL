package study.datajpa.repository

import org.springframework.stereotype.Repository
import study.datajpa.entity.Member
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

// 항상 사용자 정의 리포지토리가 필요한 것은 아니다. MemberQueryRepository를 인터페이스가 아닌 클래스로 만들고
// 스프링 빈으로 등록해서 직접 사용해도 된다
// Command + Query 분리 / 핵심 비즈니스 로직 분리
@Repository
class MemberQueryRepository(
    @PersistenceContext private val em: EntityManager
) {

    fun findAllMembers(): List<Member> {
        return em.createQuery("select m from Member m")
            .resultList as List<Member>
    }
}