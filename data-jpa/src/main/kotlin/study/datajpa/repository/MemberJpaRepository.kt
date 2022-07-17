package study.datajpa.repository

import org.springframework.stereotype.Repository
import study.datajpa.entity.Member
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

// component scan + 예외 클래스를 spring 예외 class로 처리
@Repository
class MemberJpaRepository(
    @PersistenceContext private val em: EntityManager
) {

    fun save(member: Member) : Member {
        // JPA는 변경 감지 기반으로 변경
        em.persist(member)
        return member
    }

    fun delete(member: Member) {
        em.remove(member)
    }

    fun findAll(): List<Member> {
        // JPQL
        return em.createQuery("select m from Member m", Member::class.java).resultList
    }

    fun findById(id: Long): Optional<Member> {
        val member = em.find(Member::class.java, id)
        return Optional.ofNullable(member)
    }

    fun count(): Long {
        return em.createQuery("select count(m) from Member m", Long::class.java).singleResult
    }

    fun find(id: Long): Member {
        return em.find(Member::class.java, id)
    }

    fun findByUsernameAndAgeGreaterThen(username: String, age: Int): List<Member> {
         return em.createQuery("select m from Member m where m.username = :username and m.age > :age", Member::class.java)
             .setParameter("username", username)
             .setParameter("age", age)
             .resultList
    }

    fun findByUsername(username: String): List<Member> {
        return em.createNamedQuery("Member.findByUsername", Member::class.java)
            .setParameter("username", username)
            .resultList
    }

    fun findByPage(age: Int,offset: Int,limit: Int): List<Member> {
        // JPA의 경우에는 방언(Dialect)에 따라 때문에 source DB가 달라져도 고쳐서 실행해준다
        return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
            .setParameter("age", age)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .resultList as List<Member>
    }

    fun totalCount(age: Int): Long {
        return em.createQuery("select count(M) from Member m where m.age = :age", Long::class.java)
            .setParameter("age", age)
            .singleResult
    }

    fun bulkAgePlus(age: Int): Int {
        return em.createQuery("update Member m set m.age = m.age + 1 where m.age >= :age")
            .setParameter("age", age)
            .executeUpdate()
    }

}