package secondbrain.kissle.bookshelf.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.kissle.bookshelf.domain.Book

interface LoadBookUseCase {
    fun findAll(): Uni<List<Book>>
    fun findById(id: Long): Uni<Book?>
}