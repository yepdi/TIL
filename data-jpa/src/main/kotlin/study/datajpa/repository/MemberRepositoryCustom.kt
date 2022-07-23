package study.datajpa.repository

import study.datajpa.entity.Member

public interface MemberRepositoryCustom {

    fun findMemberCustom(): List<Member>
}