package secondbrain.kissle.bookshelf.application

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.anyOrNull
import secondbrain.kissle.bookshelf.application.port.`in`.BookCopyDto
import secondbrain.kissle.bookshelf.application.port.out.CreateBookCopyPort
import secondbrain.kissle.bookshelf.application.port.out.LoadBookPort
import secondbrain.kissle.bookshelf.application.port.out.LoadBookShelfPort
import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.domain.BookCopy
import secondbrain.kissle.bookshelf.domain.BookShelf
import secondbrain.kissle.bookshelf.domain.MediumType

@QuarkusTest
class BuyBookCopyServiceTest {

    @Inject
    lateinit var buyBookCopyService: BuyBookCopyService

    @InjectMock
    lateinit var loadBookShelfPort: LoadBookShelfPort

    @InjectMock
    lateinit var loadBookPort: LoadBookPort

    @InjectMock
    lateinit var createBookCopyPort: CreateBookCopyPort

    @Test
    @RunOnVertxContext
    fun `should return Uni BookShelf with copy of the new book included`(asserter: UniAsserter) {
        asserter.execute {
            val book = Book(1L, "title", "subTitle")
            Mockito.`when`(loadBookPort.findById(anyOrNull())).thenReturn(Uni.createFrom().item(book))
            val bookShelf = BookShelf(1L, mutableListOf())
            Mockito.`when`(loadBookShelfPort.findById(anyOrNull())).thenReturn(Uni.createFrom().item(bookShelf))
            val bookCopy = BookCopy(1L, book, MediumType.HARDCOPY)
            Mockito.`when`(createBookCopyPort.create(anyOrNull())).thenReturn(Uni.createFrom().item(bookCopy))
        }
        asserter.assertEquals({
            val dto = BookCopyDto(1L, "HARDCOPY")
            buyBookCopyService.addBookCopyToDefaultShelf(1L, dto)
                .onItem().transformToUni { shelf -> Uni.createFrom().item(shelf.bookCopies.size) }
        }, 1)
        asserter.assertEquals({
            val dto = BookCopyDto(1L, "HARDCOPY")
            buyBookCopyService.addBookCopyToDefaultShelf(1L, dto)
                .onItem().transformToUni { shelf -> Uni.createFrom().item(shelf.bookCopies[0].book.title) }
        }, "title")
    }
}