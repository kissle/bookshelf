package secondbrain.bookshelf.adapter.out.persistence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import secondbrain.bookshelf.domain.Book
import secondbrain.bookshelf.port.out.CreateBookPort
import secondbrain.bookshelf.port.out.LoadBookPort

@ApplicationScoped
class BookRepository: LoadBookPort, CreateBookPort {
    override fun findAll(): Uni<List<Book>> {
        return Book.findAll().list()
    }

    override fun findById(id: Long): Uni<Book?> {
        return Book.findById(id)
            .onItem().ifNull()
            .failWith { NoSuchElementException("Book with id: $id could not be found.") }
    }

    override fun create(book: Book): Uni<Book> {
        return book.persistAndFlush()
    }
}