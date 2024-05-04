package secondbrain.kissle.bookshelf.application

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import secondbrain.kissle.bookshelf.application.port.out.CreateBookPort
import secondbrain.kissle.bookshelf.domain.Book

@QuarkusTest
class CreateBookServiceTest {

    @Inject
    lateinit var createBookService: CreateBookService

    @InjectMock
    lateinit var creatBookPort: CreateBookPort

    val book = Book(1L, "title", "subTitle")

    @Test
    @RunOnVertxContext
    fun `creates book when book does not exist in repository`(asserter: UniAsserter) {
        asserter.execute {
            Mockito.`when`(creatBookPort.create(book)).thenReturn(Uni.createFrom().item(book))
        }
        asserter.assertEquals({
            createBookService.create(book).onItem().transformToUni { book -> Uni.createFrom().item(book?.title) }
        }, "title")
    }
}