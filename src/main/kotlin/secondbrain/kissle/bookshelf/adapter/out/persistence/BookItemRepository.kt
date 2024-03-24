package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import secondbrain.kissle.bookshelf.domain.BookCopy
import secondbrain.kissle.bookshelf.port.out.CreateBookCopyPort
import secondbrain.kissle.bookshelf.port.out.LoadBookCopyPort

@ApplicationScoped
class BookItemRepository: LoadBookCopyPort, CreateBookCopyPort {
    override fun findAll(): Uni<List<BookCopy>> {
        return BookCopy.findAll().list()
    }

    override fun findById(id: Long): Uni<BookCopy?> {
        return BookCopy.findById(id)
    }

    override fun create(bookCopy: BookCopy): Uni<BookCopy> {
        return bookCopy.persistAndFlush()
    }
}