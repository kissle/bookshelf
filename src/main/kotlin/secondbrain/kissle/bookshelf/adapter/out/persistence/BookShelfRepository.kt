package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import secondbrain.kissle.bookshelf.domain.BookShelf
import secondbrain.kissle.bookshelf.port.out.CreateBookShelfPort
import secondbrain.kissle.bookshelf.port.out.LoadBookShelfPort

@ApplicationScoped
class BookShelfRepository: LoadBookShelfPort,
    CreateBookShelfPort {

    override fun create(bookShelf: BookShelf): Uni<BookShelf> {
        return bookShelf.persistAndFlush()
    }

    override fun findAll(): Uni<List<BookShelf>> {
        return BookShelf.findAll().list()
    }

    override fun findById(id: Long): Uni<BookShelf?> {
        return BookShelf.findById(id)
    }
}