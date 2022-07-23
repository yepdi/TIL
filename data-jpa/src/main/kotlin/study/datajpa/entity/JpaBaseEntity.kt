package study.datajpa.entity

import lombok.Getter
import lombok.Setter
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

// 속성들을 내려서 table 생성한다
//create table member (
//member_id bigint not null,
//created_date timestamp,
//updated_date timestamp,
//age integer,
//username varchar(255),
//team_id bigint,
//primary key (member_id)
//)
@MappedSuperclass
open class JpaBaseEntity(
    @Column(updatable = false)
    var createdDate: LocalDateTime? = null,
    var updatedDate: LocalDateTime? = null
) {

    @PrePersist
    fun prePersist() {
        val now = LocalDateTime.now()
        createdDate = now
        updatedDate = now
    }

    @PreUpdate
    fun preUpdate() {
        updatedDate = LocalDateTime.now()
    }
}