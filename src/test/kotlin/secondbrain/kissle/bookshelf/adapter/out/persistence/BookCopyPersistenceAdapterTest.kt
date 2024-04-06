package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.anyOrNull
import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.domain.BookCopy
import secondbrain.kissle.bookshelf.domain.MediumType
import org.junit.jupiter.api.Assertions.assertEquals

@QuarkusTest
class BookCopyPersistenceAdapterTest {

    @Inject
    lateinit var adapter: BookCopyPersistenceAdapter

    @InjectMock
    lateinit var repository: BookCopyRepository

    @Test
    @RunOnVertxContext
    fun `assert that a list of items is returned`(asserter: UniAsserter) {
        asserter.assertEquals({ adapter.findAll() }, emptyList())

        asserter.execute {
            val bookCopy1 = BookCopy()
            val book = Book(1,"title1", "subTitle1")
            bookCopy1.book = book
            bookCopy1.mediumType = MediumType.HARDCOPY

            Mockito.`when`(repository.listAll()).thenReturn(Uni.createFrom().item(listOf(bookCopy1)))
        }
        asserter.assertNotEquals({ adapter.findAll() }, emptyList())
    }

    @Test
    @RunOnVertxContext
    fun `assert that a book copy is created`(asserter: UniAsserter) {
        asserter.execute {
            val bookCopy = BookCopy()
            val book = Book(1L,"title", "subTitle")
            bookCopy.book = book
            bookCopy.mediumType = MediumType.HARDCOPY
            Mockito.`when`(repository.persistAndFlush(anyOrNull())).thenReturn(Uni.createFrom().item(bookCopy))
        }
        asserter.assertNotNull() {
            val bookCopy = BookCopy()
            val book = Book(1L,"title", "subTitle")
            book.title = "title"
            book.subTitle = "subTitle"
            bookCopy.book = book
            bookCopy.mediumType = MediumType.HARDCOPY
            adapter.create(bookCopy)
        }
        asserter.assertEquals({
            adapter.create(BookCopy()).onItem()
            .transformToUni { bookCopy -> Uni.createFrom().item(bookCopy.book?.title) }
                              }, "title")
    }

    @Test
    @RunOnVertxContext
    fun `assert that object with correct Id is found`(asserter: UniAsserter) {
        asserter.execute {
            val bookCopy = BookCopy()
            val book = Book(1L,"title", "subTitle")
            book.title = "title"
            book.subTitle = "subTitle"
            bookCopy.book = book
            bookCopy.mediumType = MediumType.HARDCOPY
            Mockito.`when`(repository.findById(anyOrNull())).thenReturn(Uni.createFrom().item(bookCopy))
        }
        asserter.assertNotNull { adapter.findById(1) }
        asserter.assertEquals({
            adapter.findById(1).onItem()
            .transformToUni { bookCopy -> Uni.createFrom().item(bookCopy?.book?.title) }
                              }, "title")
    }

    @Test
    @RunOnVertxContext
    fun `assert that NoSuchElementException is thrown if item does not exist`(asserter: UniAsserter) {
        asserter.execute {
            Mockito.`when`(repository.findById(anyOrNull())).thenReturn(Uni.createFrom().nullItem())
        }
        asserter.assertFailedWith({
            try {
                return@assertFailedWith adapter.findById(1)
            } catch (e: Exception) {
                return@assertFailedWith Uni.createFrom().failure(e)
            }
        }, {t -> assertEquals(NoSuchElementException::class.java, t.javaClass)})
    }
}