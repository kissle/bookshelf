package secondbrain.kissle.bookshelf.application

import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.domain.BookShelf
import secondbrain.kissle.bookshelf.application.port.`in`.LoadBookShelfUseCase
import secondbrain.kissle.bookshelf.application.port.out.LoadBookShelfPort

@ApplicationScoped
class LoadBookShelfService(
    @Inject var loadPort: LoadBookShelfPort
): LoadBookShelfUseCase {

    @WithSession
    override fun findAll(): Uni<List<BookShelf>> {
        return loadPort.findAll()
    }

    @WithSession
    override fun findById(id: Long): Uni<BookShelf?> {
        return loadPort.findById(id)
    }
}