package secondbrain.kissle.bookshelf.application

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.domain.BookCopy
import secondbrain.kissle.bookshelf.domain.BookShelf
import secondbrain.kissle.bookshelf.domain.MediumType
import secondbrain.kissle.bookshelf.port.`in`.BookCopyDto
import secondbrain.kissle.bookshelf.port.`in`.BuyBookCopyUseCase
import secondbrain.kissle.bookshelf.port.out.LoadBookPort
import secondbrain.kissle.bookshelf.port.out.LoadBookShelfPort
import secondbrain.kissle.bookshelf.port.out.UpdateBookShelfPort

@ApplicationScoped
class BuyBookCopyService: BuyBookCopyUseCase {

    @Inject
    private lateinit var loadBookShelfPort: LoadBookShelfPort

    @Inject
    private lateinit var loadBookPort: LoadBookPort

    @Inject
    private lateinit var updateBookShelfPort: UpdateBookShelfPort

    @WithTransaction
    override fun addBookCopyToDefaultShelf(bookCopyDto: BookCopyDto): Uni<BookShelf> {
        return loadBookPort.findById(bookCopyDto.bookId)
            .onItem().transformToUni { book -> createBookCopy(book, bookCopyDto) }
            .onItem().transformToUni { bookCopy ->  addBookCopyToShelf(bookCopy)}
    }

    private fun createBookCopy(book: Book?, bookCopyDto: BookCopyDto): Uni<BookCopy> {
        if (book == null) {
            return Uni.createFrom().failure(IllegalArgumentException("Book with id ${bookCopyDto.bookId} not found"))
        } else {
            val copy = BookCopy()
            copy.book = book
            copy.mediumType = MediumType.valueOf(bookCopyDto.mediumType)
            return Uni.createFrom().item(copy)
        }
    }

    private fun addBookCopyToShelf(copy: BookCopy): Uni<BookShelf> {
        return loadBookShelfPort.getDefault()
            .onItem().transformToUni { shelf -> shelf.addBook(copy) }
            .onItem().transformToUni { shelf -> updateBookShelfPort.update(shelf) }
    }
}