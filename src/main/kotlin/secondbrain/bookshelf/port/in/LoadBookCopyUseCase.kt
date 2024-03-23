package secondbrain.bookshelf.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.bookshelf.domain.BookCopy

interface LoadBookCopyUseCase {
    fun findAll(): Uni<List<BookCopy>>
    fun findById(id: Long): Uni<BookCopy?>
}