package secondbrain.bookshelf.port.out

import io.smallrye.mutiny.Uni
import secondbrain.bookshelf.domain.BookShelf

interface LoadBookShelfPort {
    fun findAll(): Uni<List<BookShelf>>
    fun findById(id: Long): Uni<BookShelf?>
}