package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.anyOrNull
import secondbrain.kissle.bookshelf.domain.Book


@QuarkusTest
class BookPersistenceAdapterTest {

    @Inject
    lateinit var adapter: BookPersistenceAdapter

    @InjectMock
    lateinit var repository: BookRepository

    @Test
    @RunOnVertxContext
    fun `assert that a list of items is returned`(asserter: UniAsserter) {
        // assert that there are no books
        asserter.assertEquals({ adapter.findAll() }, emptyList())

        asserter.execute {
            val book1 = BookEntity()
            book1.title = "title1"
            book1.subTitle = "subTitle1"

            Mockito.`when`(repository.listAll()).thenReturn(Uni.createFrom().item(listOf(book1)))
        }
        asserter.assertNotEquals({ adapter.findAll() }, emptyList())
    }

    @Test
    @RunOnVertxContext
    fun `assert that a book is created`(asserter: UniAsserter) {
        asserter.execute {
            val book = BookEntity()
            book.title = "title"
            book.subTitle = "subTitle"
            Mockito.`when`(repository.persistAndFlush(anyOrNull())).thenReturn(Uni.createFrom().item(book))
        }
        asserter.assertNotNull {
            val book = Book(1, "title1", "subTitle1")
            book.title = "title"
            book.subTitle = "subTitle"
            adapter.create(book)
        }
        asserter.assertEquals({
            adapter.create(Book(1, "title1", "subTitle1")).onItem()
            .transformToUni { book -> Uni.createFrom().item(book.title) }
                              }, "title")
    }

    @Test
    @RunOnVertxContext
    fun `assert that object with correct Id is found`(asserter: UniAsserter) {
        asserter.execute {
            val book = BookEntity()
            book.title = "title"
            book.subTitle = "subTitle"
            Mockito.`when`(repository.findById(anyOrNull())).thenReturn(Uni.createFrom().item(book))
        }
        asserter.assertNotNull {
            adapter.findById(1)
        }
        asserter.assertEquals({
            adapter.findById(1).onItem()
            .transformToUni { book -> Uni.createFrom().item(book?.title) }
                              }, "title")
    }

    @Test
    @RunOnVertxContext
    fun `assert that NoSuchElementException is thrown if item does not exist`(asserter: UniAsserter) {
        asserter.execute {
            Mockito.`when`(repository.findById(anyOrNull())).thenThrow(NoSuchElementException("Book not found"))
        }
        asserter.assertFailedWith({
            try {
                return@assertFailedWith adapter.findById(12L)
            } catch (e: Exception) {
                return@assertFailedWith Uni.createFrom().failure(e)
            }
        }, { t -> assertEquals(NoSuchElementException::class.java, t.javaClass) })
    }
}