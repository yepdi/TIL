package study.datajpa.entity

import lombok.AccessLevel
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Team(
    @Id @GeneratedValue
    @Column(name = "team_id")
    val id: Long? = null,
    val name: String,
    @OneToMany(mappedBy = "team") // mappedBy는 foreign key가 없는 쪽에서 적어주길 권장한다
    val members: List<Member> = ArrayList()
) {
    constructor(name: String) : this(null, name)

    override fun toString(): String {
        return "$id $name"
    }
}