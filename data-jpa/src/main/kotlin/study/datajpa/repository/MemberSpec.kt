package study.datajpa.repository

import org.springframework.data.jpa.domain.Specification
import study.datajpa.entity.Member
import javax.persistence.criteria.*

class MemberSpec {

    // 실무에서 사용하기에는 조금 복잡하다
    // 대신 QueryDsl을 사용하자
    companion object {
        fun teamName(teamName: String): Specification<Member> {
            return Specification<Member> { root: Root<Member>, criteriaQuery: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
                if (teamName.isEmpty()) return@Specification null
                val t = root.join<String, JoinType> ("team", JoinType.INNER)
                val name: Path<Set<String>> = t.get("name")
                return@Specification criteriaBuilder.equal(name, teamName )
            }
        }

        fun userName(username: String): Specification<Member> {
            return Specification<Member> { root: Root<Member>, criteriaQuery: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
                val usernames: Path<Set<String>> = root.get("username")
                return@Specification criteriaBuilder.equal(usernames, username)
            }
        }
    }
}