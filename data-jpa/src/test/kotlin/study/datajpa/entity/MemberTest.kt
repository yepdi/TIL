package study.datajpa.entity

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import study.datajpa.repository.MemberRepository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@SpringBootTest
@Transactional
@Rollback(false)
internal class MemberTest(
    @Autowired @PersistenceContext private val em: EntityManager,
    @Autowired private val memberRepository: MemberRepository
) {
    @Test
    fun testEntity() {
        val teamA = Team("teamA")
        val teamB = Team("teamB")
        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member("member1", 10, teamA)
        val member2 = Member("member2", 20, teamA)
        val member3 = Member("member3", 30, teamB)
        val member4 = Member("member4", 40, teamB)

        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        em.flush()
        // 강제로 db에 insert query를 날린다
        em.clear()
        // jpa 영속성 context에 있는 cache를 날린다

        val members = em.createQuery("select m from Member m", Member::class.java).resultList

        members.forEach {
            println("member $it")
            println("-> member.team ${it.team}")
        }
    }

    @Test
    fun JpaEventBaseEntity() {
        // given
        val member = Member("member1")
        memberRepository.save(member)

        Thread.sleep(100)
        member.username = "member2"

        em.flush() // @PreUpdate
        em.clear()
        // when

        val findMember = memberRepository.findById(member.id!!).get()

        // then
//        println("findMember $findMember ${findMember.createdDate} ${findMember.updatedDate}")
//        println("${findMember.modifiedBy} ${findMember.createdBy}")
    }
}