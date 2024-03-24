package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.port.out.CreateBookPort
import secondbrain.kissle.bookshelf.port.out.LoadBookPort

@ApplicationScoped
class BookRepository: LoadBookPort, CreateBookPort {
    override fun findAll(): Uni<List<Book>> {
        return Book.findAll().list()
    }

    override fun findById(id: Long): Uni<Book?> {
        return Book.findById(id)
    }

    override fun create(book: Book): Uni<Book> {
        return book.persistAndFlush()
    }
}