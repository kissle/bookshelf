package secondbrain.kissle.bookshelf.port.out

import io.smallrye.mutiny.Uni
import secondbrain.kissle.bookshelf.domain.BookCopy

interface CreateBookCopyPort {
    fun create(bookCopy: BookCopy): Uni<BookCopy>
}