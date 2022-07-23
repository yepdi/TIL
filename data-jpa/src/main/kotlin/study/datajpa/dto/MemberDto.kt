package study.datajpa.dto

import study.datajpa.entity.Member

data class MemberDto (
    var id: Long?,
    var username: String?,
    val teamName: String?
) {

    constructor(member: Member): this(member.id,  member.username, null)
}