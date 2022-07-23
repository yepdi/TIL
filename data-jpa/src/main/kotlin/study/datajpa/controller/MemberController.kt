package study.datajpa.controller

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import study.datajpa.dto.MemberDto
import study.datajpa.entity.Member
import study.datajpa.repository.MemberRepository
import javax.annotation.PostConstruct

@RestController
@RequiredArgsConstructor
class MemberController {
    @Autowired
    lateinit var memberRepository: MemberRepository

    @GetMapping("/members/{id}")
    fun findMember(@PathVariable("id")id: Long): String {
        val member = memberRepository.findById(id).get()
        return member.username!!
    }

    // PK 로 조회 후 데이터 내용 보내기
    // 도메인 클래스 컨버터가 중간에 동작해서 회원 엔티티 객체를 변환
    // 도메인 클래스 컨버터도 리파지토리를 사용해서 엔티티를 찾음 (조회용으로만)
    @GetMapping("/members2/{id}")
    fun findMember2(@PathVariable("id") member: Member): String {
       return member.username!!
    }

    // http://localhost:8080/members?page=1
    // page request 에 값을 채워준다
    // MemberDto로 반환을 해야한다 (내부 로직을 반영)
    // command option n refactoring
    @GetMapping("/members")
    fun list(@PageableDefault(size = 5) pageable: Pageable): Page<MemberDto> {

//         return memberRepository.findAll(pageable).map { member -> MemberDto(member.id, member.username, null) }
        return memberRepository.findAll(pageable).map { MemberDto(it) }
    }

    // spring application upload시 올라온다
//    @PostConstruct
    fun init() {
        for (i in 0 until 100) {
            memberRepository.save(Member("user$i", i))
        }
    }
}