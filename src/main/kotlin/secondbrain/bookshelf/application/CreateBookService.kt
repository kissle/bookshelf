package secondbrain.bookshelf.application

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.bookshelf.domain.Book
import secondbrain.bookshelf.port.`in`.CreateBookUseCase
import secondbrain.bookshelf.port.out.CreateBookPort

@ApplicationScoped
class CreateBookService: CreateBookUseCase {

    @Inject
    private lateinit var createBookPort: CreateBookPort

    @WithTransaction
    override fun create(book: Book): Uni<Book> {
        return createBookPort.create(book)
    }
}