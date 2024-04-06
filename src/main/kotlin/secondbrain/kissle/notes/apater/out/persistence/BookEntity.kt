package secondbrain.kissle.notes.apater.out.persistence

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity

@Entity
class BookEntity(
    override var id: Long?,
    val bookId: Long,
    var noteIds: MutableList<Long>
): PanacheEntity() {

    constructor(): this(null, 0, mutableListOf())

    companion object: PanacheCompanion<BookEntity>{

    }
}