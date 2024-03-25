package secondbrain.kissle.bookshelf.domain

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntity
import io.smallrye.mutiny.Uni
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
class BookShelf: PanacheEntity() {
    companion object : PanacheCompanion<BookShelf> {}

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @Fetch(FetchMode.JOIN)
    val books: MutableList<BookCopy> = mutableListOf()

    fun addBook(bookCopy: BookCopy): Uni<BookShelf> {
        books.add(bookCopy)
        return Uni.createFrom().item(this)
    }
}