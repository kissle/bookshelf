package secondbrain.kissle.bookshelf.port.out

import io.smallrye.mutiny.Uni
import secondbrain.kissle.bookshelf.domain.BookShelf

interface LoadBookShelfPort {
    fun findAll(): Uni<List<BookShelf>>
    fun findById(id: Long): Uni<BookShelf?>
}