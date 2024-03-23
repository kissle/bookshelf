package secondbrain.bookshelf.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.bookshelf.domain.Book

interface CreateBookUseCase {
    fun create(book: Book): Uni<Book>
}