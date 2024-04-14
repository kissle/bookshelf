package secondbrain.kissle.bookshelf.application

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.domain.BookShelf
import secondbrain.kissle.bookshelf.application.port.`in`.BookCopyDto
import secondbrain.kissle.bookshelf.application.port.`in`.BuyBookCopyUseCase
import secondbrain.kissle.bookshelf.application.port.out.CreateBookCopyPort
import secondbrain.kissle.bookshelf.application.port.out.LoadBookPort
import secondbrain.kissle.bookshelf.application.port.out.LoadBookShelfPort
import secondbrain.kissle.bookshelf.domain.MediumType
import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.domain.BookCopy

@ApplicationScoped
class BuyBookCopyService (
    @Inject private var loadBookPort: LoadBookPort,
    @Inject private var createBookCopyPort: CreateBookCopyPort,
    @Inject private var loadBookShelfPort: LoadBookShelfPort
): BuyBookCopyUseCase {

    override fun addBookCopyToDefaultShelf(shelfId: Long, bookCopyDto: BookCopyDto): Uni<BookShelf> {
        val bookUni = loadBookPort.findById(bookCopyDto.bookId)
        val copyUni = bookUni.onItem().transformToUni { book ->
            if (book == null) {
                Uni.createFrom().nullItem()
            } else {
                createBookCopy(book, MediumType.valueOf(bookCopyDto.mediumType))
            }
        }

        return copyUni.onItem().transformToUni { copy ->
            if (copy == null) {
                Uni.createFrom().nullItem()
            } else {
                loadBookShelfPort.findById(shelfId)
                    .onItem().transformToUni { shelf ->
                        if (shelf == null) {
                            Uni.createFrom().nullItem()
                        } else {
                            shelf.addBook(copy)
                            Uni.createFrom().item(shelf)
                        }
                    }.onItem().ifNull().failWith { NoSuchElementException("Book shelf with id $shelfId could not be found.") }
            }
        }.onItem().ifNull().failWith { IllegalArgumentException("Book not found. Could not add copy to shelf.") }
    }

    private fun createBookCopy(book: Book, type: MediumType): Uni<BookCopy> {
        val bookCopy = BookCopy(null, book, type)
        return createBookCopyPort.create(bookCopy)
    }
}