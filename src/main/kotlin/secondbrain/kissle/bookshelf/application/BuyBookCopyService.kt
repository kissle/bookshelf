package secondbrain.kissle.bookshelf.application

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import secondbrain.kissle.bookshelf.domain.BookShelf
import secondbrain.kissle.bookshelf.application.port.`in`.BookCopyDto
import secondbrain.kissle.bookshelf.application.port.`in`.BuyBookCopyUseCase

@ApplicationScoped
class BuyBookCopyService: BuyBookCopyUseCase {


    override fun addBookCopyToDefaultShelf(bookCopyDto: BookCopyDto): Uni<BookShelf> {
        TODO("Not yet implemented")
    }

//    @WithTransaction
//    override fun addBookCopyToDefaultShelf(bookCopyDto: BookCopyDto): Uni<BookShelf> {
//        return loadBookPort.findById(bookCopyDto.bookId)
//            .onItem().transformToUni { book -> createBookCopy(book, bookCopyDto) }
//           .onItem().transformToUni { bookCopy ->  addBookCopyToShelf(bookCopy)}
//    }

//    private fun createBookCopy(book: Book?, bookCopyDto: BookCopyDto): Uni<BookCopy> {
//        if (book == null) {
//            return Uni.createFrom().failure(IllegalArgumentException("Book with id ${bookCopyDto.bookId} not found"))
//        } else {
//            val copy = BookCopy()
//            copy.book = book
//            copy.mediumType = MediumType.valueOf(bookCopyDto.mediumType)
//            return Uni.createFrom().item(copy)
//        }
//    }

//    private fun addBookCopyToShelf(copy: BookCopy): Uni<BookShelf> {
//        return loadBookShelfPort.getDefault()
//            .onItem().transformToUni { shelf -> shelf.addBook(copy) }
//            .onItem().transformToUni { shelf -> updateBookShelfPort.update(shelf) }
//    }
}