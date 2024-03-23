package secondbrain.bookshelf.domain

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
class BookItem: PanacheEntity() {
    companion object : PanacheCompanion<BookItem> {
    }

    @ManyToOne
    lateinit var book: Book

    lateinit var mediumType: MediumType
}