package secondbrain.kissle.bookshelf.application.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.kissle.bookshelf.domain.BookShelf

interface BuyBookCopyUseCase {
    fun addBookCopyToDefaultShelf(bookCopyDto: BookCopyDto): Uni<BookShelf>
}