package secondbrain.bookshelf.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.bookshelf.domain.BookCopy

interface CreateBookCopyUseCase {
    fun create(bookCopy: BookCopyDto): Uni<BookCopy>
}