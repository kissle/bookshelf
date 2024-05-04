package secondbrain.kissle.bookshelf.application

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.adapter.out.persistence.BookShelfMapper
import secondbrain.kissle.bookshelf.domain.BookShelf
import secondbrain.kissle.bookshelf.application.port.`in`.CreateBookShelfUseCase
import secondbrain.kissle.bookshelf.application.port.out.CreateBookShelfPort

@ApplicationScoped
class CreateBookShelfService(
    @Inject
    private var createBookShelfPort: CreateBookShelfPort
): CreateBookShelfUseCase {

    private val mapper = BookShelfMapper()

    override fun create(): Uni<BookShelf> {
       return createBookShelfPort.create().onItem().transformToUni { shelf ->
           Uni.createFrom().item(mapper.toDomain(shelf.id, mutableListOf()))
       }
    }
}