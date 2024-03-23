package secondbrain.bookshelf.port.out

import io.smallrye.mutiny.Uni
import secondbrain.bookshelf.domain.BookCopy

interface CreateBookCopyPort {
    fun create(bookCopy: BookCopy): Uni<BookCopy>
}