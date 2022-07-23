package study.datajpa.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.*
import org.springframework.data.repository.query.Param
import study.datajpa.dto.MemberDto
import study.datajpa.entity.Member
import java.util.*
import javax.persistence.LockModeType
import javax.persistence.QueryHint

interface MemberRepository : JpaRepository<Member, Long>, MemberRepositoryCustom, JpaSpecificationExecutor<Member> {

    fun findByUsernameAndAgeGreaterThan(username: String, age: Int): List<Member>
    // method 이름으로 Query를 만든다

    fun findTop3By(): List<Member>

    // JPQL을 해당 Query로 찾는다
    // Query를 지워도 된다. namedQuery를 찾고나서 메소드 이름으로 쿼리 생성한다
    @Query(name = "Member.findByUsername")
    fun findByUsername(@Param("username") username: String): List<Member>

    // 메소드 이름이 길어지는 경우 JPQL을 사용하여 메소드 명을 간단하게 할 수 있다
    @Query("select m from Member m where m.username = :username and m.age = :age")
    fun findUser(@Param("username") username: String, @Param("age") age: Int): List<Member>

    @Query("select m.username from Member m")
    fun findUsernameList(): List<String>

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t ")
    fun findMemberDto(): List<MemberDto>

    // parameter 바인 (names)로 가능
    @Query("select m from Member m where m.username in :names")
    fun findByNames(@Param("names") names: List<String>): List<Member>

    fun findListByUsername(username: String): List<Member>

    fun findMemberByUsername(username: String): Member

    fun findOptionalByUsername(username: String): Optional<Member>

    // org.springframework.data.domain.Sort 정렬
    // org.springframework.data.domain.Pageable 페이징 기능(내부에 Sort 포함)

    // org.springframework.data.domain.Page : 추가 count 쿼리 결과 포함
    // org.springframework.data.domain.Slice : 추가 count 쿼리 없이 다음 페이지만 확인 가능 (내부적으로 Limit + 1 조회)

    // left outer join을 하는 경우에는 join 하지 않고 countQuery를 분리하여 사용할 수 있다 (매번 count를 하면 성능에 이슈가 될 수 있기 때문)
    @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m.username) from Member m")
    fun findByAge(age: Int, pageable: Pageable): Page<Member>

    fun findSliceByAge(age: Int, pageable: Pageable): Slice<Member>

    // 변경 감지(Modifying)를 꼭 넣어줘야한다
    // 영속성 context를 초기화해주는 option을 꼭 넣자
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    fun bulkAgePlus(@Param("age") age:Int): Int
    // JPA의 bulk성 업데이트는 주의해야한다
    // 영속성 context를 무시하고 바로 db에 업데이트를 치기 때문이,

    // 연관된 entity도 가져온다 (join + data 가져오기)
    @Query("select m from Member m left join fetch m.team")
    fun findMemberFetchJoin(): List<Member>

    // JPQL 없이 명시적으로 넣으면 된다
    @EntityGraph(attributePaths = ["team"])
    override fun findAll(): List<Member>

    @EntityGraph(attributePaths = ["team"])
    @Query("select m from Member m")
    fun findMemberEntityGraph(): List<Member>

    @EntityGraph(attributePaths = ["team"])
    // named로 사용할 수 있다
//    @EntityGraph("Member.all")
    fun findEntityGraphByUsername(@Param("username") username: String):List<Member>

    // 변경 자체를 하지 않는다 (원래는 snapshot 만들어서 관리.. 최적화)
    @QueryHints(value = [QueryHint(name = "org.hibernate.readOnly", value= "true")])
    fun findReadOnlyByUsername(username: String): Member

    // JPA가 제공하는 Lock이 달라진다
    // 실시간 traffic이 많은 경우 lock을 지양하자 / 금융 권에서는 많이 사용
    // optimistic lock
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findLockByUsername(name: String): List<Member>
}