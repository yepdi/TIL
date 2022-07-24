package study.datajpa.repository

import org.springframework.beans.factory.annotation.Value

interface UserNameOnly {

    // username과 age를 둘다 가져와서 넣을 수 있다 entity에 있는 내용을 다 가져온다 (open projection)
    @Value("#{target.username + ' ' + target.age}")
    fun getUsername(): String
    // 실제 구현체는 spring data jpa에서 만들어서 반환한다
}