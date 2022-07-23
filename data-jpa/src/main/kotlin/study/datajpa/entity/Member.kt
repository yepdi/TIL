package study.datajpa.entity

import lombok.AccessLevel
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedQuery(
    name = "Member.findByUsername",
    query = "select m from Member m where m.username = :username"
)
// namedQuery는 application loading 시점에 parsing을 해서 application loading 시점에 버그를 잡을 수 있다
@NamedEntityGraph(name = "Member.all", attributeNodes = [NamedAttributeNode("team")])
data class Member (
    @Id @GeneratedValue
    @Column(name = "member_id")
    val id: Long? = null,
    var username: String?,
    val age: Int? = null,
    @ManyToOne(fetch = FetchType.LAZY) // JPA 모든 연관관계는 Lazy 로 Setting (성능 최적화를 위해서..)
    @JoinColumn(name = "team_id")
    var team: Team? = null
): BaseEntity(null, null) {
    constructor(username: String): this(null, username)
    constructor(username: String, age: Int, team: Team) : this(null, username, age, null) {
        changeTeam(team)
    }

    constructor(id: String, username: Int) : this(null, id, username)

    fun changeTeam(team: Team) {
        this.team = team
        team.members.toMutableList().add(this)
    }
}