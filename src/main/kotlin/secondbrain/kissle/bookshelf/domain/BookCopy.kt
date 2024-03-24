package secondbrain.kissle.bookshelf.domain

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType

@Entity
class BookCopy: PanacheEntity() {
    companion object : PanacheCompanion<BookCopy> {
    }

    @ManyToOne
    @Cascade(CascadeType.PERSIST)
    lateinit var book: Book

    lateinit var mediumType: MediumType
}