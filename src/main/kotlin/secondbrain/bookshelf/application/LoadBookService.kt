package secondbrain.bookshelf.application

import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.bookshelf.domain.Book
import secondbrain.bookshelf.port.`in`.LoadBookUseCase
import secondbrain.bookshelf.port.out.LoadBookPort

@ApplicationScoped
class LoadBookService: LoadBookUseCase {

    @Inject
    private lateinit var loadBookPort: LoadBookPort

    @WithSession
    override fun findAll(): Uni<List<Book>> {
        return loadBookPort.findAll()
    }

    @WithSession
    override fun findById(id: Long): Uni<Book?> {
        return loadBookPort.findById(id).onItem()
            .ifNull().failWith { NoSuchElementException("No Book with id $id could be found.") }
    }
}