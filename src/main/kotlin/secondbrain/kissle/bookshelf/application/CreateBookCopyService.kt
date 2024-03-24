package secondbrain.kissle.bookshelf.application

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.domain.BookCopy
import secondbrain.kissle.bookshelf.domain.MediumType
import secondbrain.kissle.bookshelf.port.`in`.BookCopyDto
import secondbrain.kissle.bookshelf.port.`in`.CreateBookCopyUseCase
import secondbrain.kissle.bookshelf.port.`in`.LoadBookUseCase
import secondbrain.kissle.bookshelf.port.out.CreateBookCopyPort

@ApplicationScoped
class CreateBookCopyService: CreateBookCopyUseCase {

    @Inject
    private lateinit var createBookCopyPort: CreateBookCopyPort

    @Inject
    private lateinit var loadBookUseCase: LoadBookUseCase

    @WithTransaction
    override fun create(bookCopy: BookCopyDto): Uni<BookCopy> {
        return loadBookUseCase.findById(bookCopy.bookId).onItem().transformToUni { book ->
            if (book == null) {
                Uni.createFrom().failure(IllegalArgumentException("Book with id ${bookCopy.bookId} not found"))
            } else {
                val copy = BookCopy()
                copy.book = book
                copy.mediumType = MediumType.valueOf(bookCopy.mediumType)
                createBookCopyPort.create(copy)
            }
        }
    }
}