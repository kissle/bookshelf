package secondbrain.kissle.bookshelf.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.kissle.bookshelf.domain.BookShelf

interface LoadBookShelfUseCase {
    fun findAll(): Uni<List<BookShelf>>
    fun findById(id: Long): Uni<BookShelf?>
}