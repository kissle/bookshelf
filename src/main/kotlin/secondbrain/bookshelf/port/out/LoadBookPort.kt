package secondbrain.bookshelf.port.out

import io.smallrye.mutiny.Uni
import secondbrain.bookshelf.domain.Book

interface LoadBookPort {
    fun findAll(): Uni<List<Book>>
    fun findById(id: Long): Uni<Book?>
}