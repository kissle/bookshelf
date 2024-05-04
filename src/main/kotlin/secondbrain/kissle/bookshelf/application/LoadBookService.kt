package secondbrain.kissle.bookshelf.application

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.application.port.`in`.LoadBookUseCase
import secondbrain.kissle.bookshelf.application.port.out.LoadBookPort

@ApplicationScoped
class LoadBookService: LoadBookUseCase {

    @Inject
    private lateinit var loadBookPort: LoadBookPort

    override fun findAll(): Uni<List<Book>> {
        return loadBookPort.findAll()
    }

    override fun findById(id: Long): Uni<Book?> {
        return loadBookPort.findById(id)
    }
}