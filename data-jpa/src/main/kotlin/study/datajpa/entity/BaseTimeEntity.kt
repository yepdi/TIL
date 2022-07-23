package study.datajpa.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseTimeEntity (
    @CreatedDate
    @Column(updatable = false) private var createdDate: LocalDateTime? = null,
    @LastModifiedDate
    private var updatedDate: LocalDateTime? = null,
)
// createdDate, updatedDate의 경우에는 많이 사용하여 Entity 분리