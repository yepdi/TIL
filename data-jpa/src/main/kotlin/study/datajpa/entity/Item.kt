package study.datajpa.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Id

@Entity
@EntityListeners(AuditingEntityListener::class)
class Item(
//    @Id @GeneratedValue
//    val id: Long? = null,
    @Id
    val identifier: String,
    @CreatedDate
    private var createdDate: LocalDateTime? = null
): Persistable<String> {

    override fun getId(): String? = identifier

    override fun isNew(): Boolean {
        return createdDate == null
    }
}