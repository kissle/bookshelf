package secondbrain.kissle.bookshelf.application.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.kissle.bookshelf.domain.BookCopy

interface CreateBookCopyUseCase {
    fun create(bookCopy: BookCopyDto): Uni<BookCopy>
}