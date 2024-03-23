package secondbrain.bookshelf.port.out

import io.smallrye.mutiny.Uni
import secondbrain.bookshelf.domain.BookCopy

interface LoadBookCopyPort {
    fun findAll(): Uni<List<BookCopy>>
    fun findById(id: Long): Uni<BookCopy?>
}