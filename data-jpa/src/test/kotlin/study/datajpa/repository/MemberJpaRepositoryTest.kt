package study.datajpa.repository

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import study.datajpa.entity.Member

@SpringBootTest
@Transactional // rollback이 됨 @Rollback(false)
internal class MemberJpaRepositoryTest(
    @Autowired private val memberJpaRepository: MemberJpaRepository
) {

    @Test
    fun testMember() {
        val member = Member("memberA")
        val savedMember = memberJpaRepository.save(member)
        val findMember = memberJpaRepository.find(savedMember.id!!)
        // Transactional을 걸어서 같은 영속성 entity 인것을 보장한다
        // Transaction이 다르면 다른 entity

        assertThat(findMember.id).isEqualTo(member.id)
        assertThat(findMember.username).isEqualTo(member.username)
        assertThat(findMember).isEqualTo(savedMember)
    }

    @Test
    fun basicCRUD() {
        val member1 = Member("member1")
        val member2 = Member("member2")
        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)

        val findmember1 = memberJpaRepository.findById(member1.id!!).get()
        val findmember2 = memberJpaRepository.findById(member2.id!!).get()

        // 단건 조회
        assertThat(findmember1).isEqualTo(member1)
        assertThat(findmember2).isEqualTo(member2)

        // 리스트 조회
        val members = memberJpaRepository.findAll()
        assertThat(members.size).isEqualTo(2)

//        var count = memberJpaRepository.count()
//        assertThat(count).isEqualTo(2)

        memberJpaRepository.delete(member1)
        memberJpaRepository.delete(member2)

//        count = memberJpaRepository.count()
//        assertThat(count).isEqualTo(0)
    }

    @Test
    fun findByUsernameAndAgeGreaterThen() {
        val member1 = Member("AAA", 10)
        val member2 = Member("AA", 20)

        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)

        val result = memberJpaRepository.findByUsernameAndAgeGreaterThen("AA", 15)
        assertThat(result[0].username).isEqualTo("AA")
        assertThat(result[0].age).isEqualTo(20)
    }

    @Test
    fun namedQuery() {
        val member1 = Member("AA", 10)
        val member2 = Member("BBB", 20)

        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)

        val result = memberJpaRepository.findByUsername("AA")
        val findMember = result[0]
        assertThat(findMember).isEqualTo(member1)
    }

    @Test
    fun paging() {
        // given
        memberJpaRepository.save(Member("member1", 10))
        memberJpaRepository.save(Member("member2", 10))
        memberJpaRepository.save(Member("member3", 10))
        memberJpaRepository.save(Member("member4", 10))
        memberJpaRepository.save(Member("member5", 10))

        val age = 10
        val offset = 0
        val limit = 3

        // when
        val members = memberJpaRepository.findByPage(age, offset, limit)
        members?.forEach {
            println(it)
        }
        //val totalCount = memberJpaRepository.totalCount(age)

        // then
        assertThat(members?.size).isEqualTo(3)
//        assertThat(totalCount).isEqualTo(5)

    }

    @Test
    fun bulkUpdate() {
        memberJpaRepository.save(Member("member1", 10))
        memberJpaRepository.save(Member("member2", 19))
        memberJpaRepository.save(Member("member3", 20))
        memberJpaRepository.save(Member("member4", 21))
        memberJpaRepository.save(Member("member5", 40))

        // when
        val resultCount = memberJpaRepository.bulkAgePlus(20)

        assertThat(resultCount).isEqualTo(3)
    }
}