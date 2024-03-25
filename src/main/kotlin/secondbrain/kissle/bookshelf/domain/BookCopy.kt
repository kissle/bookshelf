package secondbrain.kissle.bookshelf.domain

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
class BookCopy: PanacheEntity() {
    companion object : PanacheCompanion<BookCopy> {
    }

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @Fetch(FetchMode.JOIN)
    lateinit var book: Book

    lateinit var mediumType: MediumType
}