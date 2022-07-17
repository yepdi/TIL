package study.datajpa.repository

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import study.datajpa.dto.MemberDto
import study.datajpa.entity.Member
import study.datajpa.entity.Team
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest(
    @Autowired @PersistenceContext private val em: EntityManager,
    @Autowired private val memberRepository: MemberRepository,
    @Autowired private val teamRepository: TeamRepository,
) {

    // org.springframework.data 공통 (spring-commons)
    // data.jpa jpa 특화
    @Test
    fun getclass() {
        // spring data jpa 가 구현 클래스를 만들어준다
        // memberRepository class com.sun.proxy.$Proxy112
        println("memberRepository ${memberRepository.javaClass}")
    }
    @Test
    fun testMember() {
        val member = Member("memberA")
        val savedMember = memberRepository.save(member)
        val findMember = memberRepository.findById(savedMember.id!!).get()
        // Transactional을 걸어서 같은 영속성 entity 인것을 보장한다
        // Transaction이 다르면 다른 entity

        Assertions.assertThat(findMember.id).isEqualTo(member.id)
        Assertions.assertThat(findMember.username).isEqualTo(member.username)
        Assertions.assertThat(findMember).isEqualTo(savedMember)
    }

    @Test
    fun basicCRUD() {
        val member1 = Member("member1")
        val member2 = Member("member2")
        memberRepository.save(member1)
        memberRepository.save(member2)

        val findmember1 = memberRepository.findById(member1.id!!).get()
        val findmember2 = memberRepository.findById(member2.id!!).get()

        // 단건 조회
        Assertions.assertThat(findmember1).isEqualTo(member1)
        Assertions.assertThat(findmember2).isEqualTo(member2)

        // 리스트 조회
        val members = memberRepository.findAll()
        Assertions.assertThat(members.size).isEqualTo(2)

//        var count = memberJpaRepository.count()
//        assertThat(count).isEqualTo(2)

        memberRepository.delete(member1)
        memberRepository.delete(member2)

//        count = memberJpaRepository.count()
//        assertThat(count).isEqualTo(0)
    }

    @Test
    fun findByUsernameAndAgeGreaterThen() {
        val member1 = Member("AAA", 10)
        val member2 = Member("AA", 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findByUsernameAndAgeGreaterThan("AA", 15)
        Assertions.assertThat(result[0].username).isEqualTo("AA")
        Assertions.assertThat(result[0].age).isEqualTo(20)
    }

    @Test
    fun findTop3By() {
        val members = memberRepository.findTop3By()
    }

    @Test
    fun testNamedQuery() {
        val member1 = Member("AA", 10)
        val member2 = Member("BBB", 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findByUsername("AA")
        val findMember = result[0]
        Assertions.assertThat(findMember).isEqualTo(member1)
    }

    @Test
    fun testQuery() {
        val member1 = Member("AAA", 10)
        val member2 = Member("BBB", 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val result = memberRepository.findUser("AAA", 10)
        val findMember = result[0]
        Assertions.assertThat(findMember).isEqualTo(member1)
    }

    @Test
    fun findUsernameList() {
        val member1 = Member("AAA", 10)
        val member2 = Member("BBB", 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val usernameList = memberRepository.findUsernameList()
        usernameList.forEach { s ->
            println(s)
        }
    }

    @Test
    fun findMemberDto() {
        val team = Team("teamA")
        teamRepository.save(team)

        val member1 = Member("AAA", 10)
        member1.team = team
        memberRepository.save(member1)

        val memberDto = memberRepository.findMemberDto()
        memberDto.forEach { s ->
            println(s)
        }
    }

    @Test
    fun findByNames() {
        val member1 = Member("AAA", 10)
        val member2 = Member("BBB", 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val usernameList = memberRepository.findByNames(listOf("AAA", "BBB"))

        usernameList.forEach { s ->
            println(s)
        }
    }

    @Test
    fun returnType() {
        val member1 = Member("AAA", 10)
        val member2 = Member("BBB", 20)

        memberRepository.save(member1)
        memberRepository.save(member2)

        val aaa = memberRepository.findListByUsername("AAA")
        println(aaa)

        val wrong = memberRepository.findListByUsername("asdfasfd")
        // 값이 없는 경우에는 empty List로 반환된다

        val wrongMember = memberRepository.findMemberByUsername("asdfasfd")
        // spring jpa 에서는 값이 없는 경우에는 NoResultException 에서는 null로 반환해준다

        val findMember = memberRepository.findMemberByUsername("AAA")
        println(findMember)

        val optionalMember = memberRepository.findOptionalByUsername("AAA")
        println(optionalMember.get())
        // spring이 예외를 바꿔준다
        // 결과가 2건 이상인 경우에는 NonUniqueException -> spring쪽에서 IncorrectResultSizeDataAcessException 으로 바꿔진다
    }

    @Test
    fun paging() {
        // given
        memberRepository.save(Member("member1", 10))
        memberRepository.save(Member("member2", 10))
        memberRepository.save(Member("member3", 10))
        memberRepository.save(Member("member4", 10))
        memberRepository.save(Member("member5", 10))

        val age = 10
        // Sort 조건이 복잡하다면 JPQL에 넣자
        val pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"))
        // interface가 Pageable임 pageRequest는 구현체

        // when
        val pages = memberRepository.findByAge(age, pageRequest)
        // count 쿼리도 같이 나간다

        val toMap = pages.map { member -> MemberDto(member.id, member.username, null) }
        // dto로 반환해서 API류 반환

        // then
        val content = pages.content
        val totalElements = pages.totalElements

        content.forEach {
            println(it)
        }
        println(totalElements)

        Assertions.assertThat(content.size).isEqualTo(3)
        Assertions.assertThat(pages.totalElements).isEqualTo(5)
        Assertions.assertThat(pages.number).isEqualTo(0)
        Assertions.assertThat(pages.totalPages).isEqualTo(2)
        Assertions.assertThat(pages.isFirst).isTrue
        Assertions.assertThat(pages.hasNext()).isTrue
    }

    @Test
    fun pagingSlice() {
        // given
        memberRepository.save(Member("member1", 10))
        memberRepository.save(Member("member2", 10))
        memberRepository.save(Member("member3", 10))
        memberRepository.save(Member("member4", 10))
        memberRepository.save(Member("member5", 10))

        val age = 10
        val pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"))
        // select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team_id as team_id4_0_, member0_.username as username3_0_ from member member0_ where member0_.age=10 order by member0_.username desc limit 4;
        // limit 가 4이다

        // when
        val pages = memberRepository.findSliceByAge(age, pageRequest)
        // count 쿼리도 같이 나간다

        // then
        val content = pages.content
//        val totalElements = pages.totalElements

        content.forEach {
            println(it)
        }
//        println(totalElements)

        Assertions.assertThat(content.size).isEqualTo(3)
//        Assertions.assertThat(pages.totalElements).isEqualTo(5)
        Assertions.assertThat(pages.number).isEqualTo(0)
//        Assertions.assertThat(pages.totalPages).isEqualTo(2)
        Assertions.assertThat(pages.isFirst).isTrue
        Assertions.assertThat(pages.hasNext()).isTrue
    }

    @Test
    fun bulkUpdate() {
        // save 는 db에 flush한다
        // 영속성 context에 있는 것들은 db에 다 보낸 (flush)
        memberRepository.save(Member("member1", 10))
        memberRepository.save(Member("member2", 19))
        memberRepository.save(Member("member3", 20))
        memberRepository.save(Member("member4", 21))
        memberRepository.save(Member("member5", 40))

        // when
        // db 업데이트
        val resultCount = memberRepository.bulkAgePlus(20)
//        em.flush()
//        em.clear()
//        영속성 context를 초기화해주는 clearAutomatically = true를 꼭 넣자
        // 같은 transaction 내에서 로직이 수행되면 큰일난다.. ㅜ

        // JPA 영속성 (1차 cache) 기반 연산
        val findMembers = memberRepository.findByUsername("member5")
        val member = findMembers[0]
        // 결과 값은? 40
        println(member)

        Assertions.assertThat(resultCount).isEqualTo(3)
    }

    @Test
    fun findMemberLazy() {
        // given
        // member1 -> teamA
        // member2 -> teamB

        val teamA = Team("teamA")
        val teamB = Team("teamB")

        teamRepository.save(teamA)
        teamRepository.save(teamB)

        val member1 = Member("Member1", 10, teamA)
        val member2 = Member("Member2", 10, teamB)

        memberRepository.save(member1)
        memberRepository.save(member2)

        em.flush()
        em.clear()

        // when N + 1 문제 성능 문제
        val members = memberRepository.findAll()
//        val members = memberRepository.findEntityGraphByUsername("member1")
        // member만 db에서 가져온다

//        val members = memberRepository.findMemberFetchJoin()

        members.forEach {
            println(it.username)
            println(it.team?.javaClass)
            println(it.team?.name)
        }
    }

    @Test
    fun queryhint() {
        // given
        val member1 = memberRepository.save(Member("member1", 10))
        em.flush() // 영속성 context에 남아 있고 db에 동기화 한다
        em.clear() // 영속성 context가 날라간다

        // when
//        val findMember = memberRepository.findById(member1.id!!).get()
        val findMember = memberRepository.findReadOnlyByUsername("member1")
        // QueryHints를 통해 변경 감지 체크 자체를 하지 않는다
        findMember.username = "member2"

        // update member set age=10, team_id=NULL, username='member2' where member_id=1;
        em.flush()
    }

    @Test
    fun lock() {
        // given
        val member1 = memberRepository.save(Member("member1", 10))
        em.flush() // 영속성 context에 남아 있고 db에 동기화 한다
        em.clear() // 영속성 context가 날라간다

        val findMember = memberRepository.findLockByUsername("member1")
        // dialect에 따라서 동작이 달라진다
        //     select
        //        member0_.member_id as member_i1_0_,
        //        member0_.age as age2_0_,
        //        member0_.team_id as team_id4_0_,
        //        member0_.username as username3_0_
        //    from
        //        member member0_
        //    where
        //        member0_.username=? for update
    }
}