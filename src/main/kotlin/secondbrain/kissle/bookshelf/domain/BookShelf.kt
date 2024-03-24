package secondbrain.kissle.bookshelf.domain

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class BookShelf: PanacheEntity() {
    companion object : PanacheCompanion<BookShelf> {}

    @OneToMany
    val books: MutableList<BookCopy> = mutableListOf()
}