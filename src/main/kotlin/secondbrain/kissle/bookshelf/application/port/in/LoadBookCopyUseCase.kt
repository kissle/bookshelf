package secondbrain.kissle.bookshelf.application.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.kissle.bookshelf.domain.BookCopy

interface LoadBookCopyUseCase {
    fun findAll(): Uni<List<BookCopy>>
    fun findById(id: Long): Uni<BookCopy>
}