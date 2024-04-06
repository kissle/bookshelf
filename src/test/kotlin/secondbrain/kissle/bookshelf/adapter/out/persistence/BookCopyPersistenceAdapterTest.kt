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
import org.mockito.kotlin.any
import secondbrain.kissle.bookshelf.application.port.out.LoadBookPort

@QuarkusTest
class BookCopyPersistenceAdapterTest {

    @Inject
    lateinit var adapter: BookCopyPersistenceAdapter

    @InjectMock
    lateinit var repository: BookCopyRepository

    @InjectMock
    lateinit var loadBookPort: LoadBookPort

    @Test
    @RunOnVertxContext
    fun `assert that a list of items is returned`(asserter: UniAsserter) {
        asserter.assertEquals({ adapter.findAll() }, emptyList())

        asserter.execute {
            val bookCopy1 = BookCopyEntity()
            val book = Book(1,"title1", "subTitle1")
            bookCopy1.bookId = book.id
            bookCopy1.type = MediumType.HARDCOPY

            Mockito.`when`(repository.listAll()).thenReturn(Uni.createFrom().item(listOf(bookCopy1)))
            Mockito.`when`(loadBookPort.findById(any())).thenReturn(Uni.createFrom().item(book))
        }
        asserter.assertNotEquals({ adapter.findAll() }, emptyList())
    }

    @Test
    @RunOnVertxContext
    fun `assert that a book copy is created`(asserter: UniAsserter) {
        asserter.execute {
            val bookCopy = BookCopyEntity()
            val book = Book(1L,"title", "subTitle")
            bookCopy.bookId = book.id
            bookCopy.type = MediumType.HARDCOPY
            Mockito.`when`(repository.persistAndFlush(anyOrNull())).thenReturn(Uni.createFrom().item(bookCopy))
            Mockito.`when`(loadBookPort.findById(any())).thenReturn(Uni.createFrom().item(book))
        }
        asserter.assertNotNull() {
            val book = Book(1L,"title", "subTitle")
            val bookCopy = BookCopy(1L, book, MediumType.HARDCOPY)
            adapter.create(bookCopy)
        }
        asserter.assertEquals({
            adapter.create(BookCopy(1L, Book(1L, "title", "subTitle"), MediumType.HARDCOPY)).onItem()
            .transformToUni { bookCopy -> Uni.createFrom().item(bookCopy.book.title) }
                              }, "title")
    }

    @Test
    @RunOnVertxContext
    fun `assert that object with correct Id is found`(asserter: UniAsserter) {
        asserter.execute {
            val book = Book(1L,"title", "subTitle")
            val bookCopy = BookCopyEntity()
            bookCopy.bookId = 1L
            bookCopy.type = MediumType.HARDCOPY
            Mockito.`when`(repository.findById(anyOrNull())).thenReturn(Uni.createFrom().item(bookCopy))
            Mockito.`when`(loadBookPort.findById(any())).thenReturn(Uni.createFrom().item(book))
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
            Mockito.`when`(loadBookPort.findById(anyOrNull())).thenReturn(Uni.createFrom().nullItem())
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