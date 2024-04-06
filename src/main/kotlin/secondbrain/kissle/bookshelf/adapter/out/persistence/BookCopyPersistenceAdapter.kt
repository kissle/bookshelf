package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.application.port.out.CreateBookCopyPort
import secondbrain.kissle.bookshelf.application.port.out.LoadBookCopyPort
import secondbrain.kissle.bookshelf.domain.BookCopy

@ApplicationScoped
class BookCopyPersistenceAdapter(
   @Inject var bookCopyRepository: BookCopyRepository,
): LoadBookCopyPort, CreateBookCopyPort {
    override fun create(bookCopy: BookCopy): Uni<BookCopy> {
        return bookCopyRepository.persistAndFlush(bookCopy)
    }

    override fun findAll(): Uni<List<BookCopy>> {
        return bookCopyRepository.listAll()
    }

    override fun findById(id: Long): Uni<BookCopy?> {
        return bookCopyRepository.findById(id)
            .onItem()
            .ifNull()
            .failWith { NoSuchElementException("BookCopy not found") }
    }
}