package secondbrain.bookshelf.adapter.out.persistence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import secondbrain.bookshelf.domain.BookCopy
import secondbrain.bookshelf.port.out.CreateBookCopyPort
import secondbrain.bookshelf.port.out.LoadBookCopyPort

@ApplicationScoped
class BookItemRepository: LoadBookCopyPort, CreateBookCopyPort {
    override fun findAll(): Uni<List<BookCopy>> {
        return BookCopy.findAll().list()
    }

    override fun findById(id: Long): Uni<BookCopy?> {
        return BookCopy.findById(id).onItem()
            .ifNull().failWith { NoSuchElementException("Book copy with id $id could not be found") }
    }

    override fun create(bookCopy: BookCopy): Uni<BookCopy> {
        return bookCopy.persistAndFlush()
    }
}