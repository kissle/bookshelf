package secondbrain.kissle.bookshelf.application

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.application.port.`in`.CreateBookUseCase
import secondbrain.kissle.bookshelf.application.port.out.CreateBookPort

@ApplicationScoped
class CreateBookService: CreateBookUseCase {

    @Inject
    private lateinit var createBookPort: CreateBookPort

    override fun create(book: Book): Uni<Book> {
        return createBookPort.create(book)
    }
}