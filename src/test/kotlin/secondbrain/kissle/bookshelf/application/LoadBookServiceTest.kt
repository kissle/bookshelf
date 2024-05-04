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
import secondbrain.kissle.bookshelf.application.port.out.LoadBookPort
import secondbrain.kissle.bookshelf.domain.Book

@QuarkusTest
class LoadBookServiceTest {

    @InjectMock
    lateinit var  loadBookPort: LoadBookPort

    @Inject
    lateinit var loadBookService: LoadBookService

    @Test
    @RunOnVertxContext
    fun `loads book when book exists in repository`(asserter: UniAsserter) {
        asserter.execute {
            val book = Book(1L, "title", "subTitle")
            Mockito.`when`(loadBookPort.findById(anyOrNull())).thenReturn(Uni.createFrom().item(book))
        }
        asserter.assertEquals({
            loadBookService.findById(1L).onItem().transformToUni { book -> Uni.createFrom().item(book?.title) }
        }, "title")
    }

    @Test
    @RunOnVertxContext
    fun `load all Books from repository`(asserter: UniAsserter) {
        asserter.execute {
            val book1 = Book(1L, "title1", "subTitle1")
            val book2 = Book(2L, "title2", "subTitle2")
            Mockito.`when`(loadBookPort.findAll()).thenReturn(Uni.createFrom().item(listOf(book1, book2)))
        }
        asserter.assertEquals({
            loadBookService.findAll().onItem().transformToUni { books -> Uni.createFrom().item(books.map { it.title }) }
        }, listOf("title1", "title2"))
    }
}