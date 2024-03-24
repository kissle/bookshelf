package secondbrain.kissle.bookshelf.application

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.port.`in`.CreateBookUseCase
import secondbrain.kissle.bookshelf.port.out.CreateBookPort

@ApplicationScoped
class CreateBookService: CreateBookUseCase {

    @Inject
    private lateinit var createBookPort: CreateBookPort

    @WithTransaction
    override fun create(book: Book): Uni<Book> {
        return createBookPort.create(book)
    }
}