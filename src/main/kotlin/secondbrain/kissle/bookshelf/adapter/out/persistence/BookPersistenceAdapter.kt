package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.application.port.out.CreateBookPort
import secondbrain.kissle.bookshelf.application.port.out.LoadBookPort
import secondbrain.kissle.bookshelf.domain.Book

@ApplicationScoped
class BookPersistenceAdapter(
    @Inject var bookRepository: BookRepository
): LoadBookPort, CreateBookPort {

    override fun create(book: Book): Uni<Book> {
        return bookRepository.persistAndFlush(book)
    }

    override fun findAll(): Uni<List<Book>> {
        return bookRepository.listAll()
    }

    override fun findById(id: Long): Uni<Book?> {
        return bookRepository.findById(id)
            .onItem()
            .ifNull()
            .failWith { NoSuchElementException("Book not found") }
    }
}