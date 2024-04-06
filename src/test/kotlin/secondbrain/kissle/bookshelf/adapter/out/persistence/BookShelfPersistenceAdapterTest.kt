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
import secondbrain.kissle.bookshelf.domain.BookShelf

@QuarkusTest
class BookShelfPersistenceAdapterTest {

    @Inject
    lateinit var bookShelfPersistenceAdapter: BookShelfPersistenceAdapter

    @InjectMock
    lateinit var bookShelfRepository: BookShelfRepository

    @Test
    @RunOnVertxContext
    fun `assert that a list of items is returned`(asserter: UniAsserter) {
        asserter.assertEquals({ bookShelfPersistenceAdapter.findAll() }, emptyList())

        asserter.execute {
            val bookShelf1 = BookShelf()

            Mockito.`when`(bookShelfRepository.listAll()).thenReturn(Uni.createFrom().item(listOf(bookShelf1)))
        }
        asserter.assertNotEquals({ bookShelfPersistenceAdapter.findAll() }, emptyList())
    }

    @Test
    @RunOnVertxContext
    fun `assert that a object with correct id is found`(asserter: UniAsserter) {
        asserter.execute {
            val bookShelf = BookShelf()
            bookShelf.id = 1L
            Mockito.`when`(bookShelfRepository.findById(1)).thenReturn(Uni.createFrom().item(bookShelf))
        }
        asserter.assertNotNull() {
            bookShelfPersistenceAdapter.findById(1)
        }
        asserter.assertEquals({
            bookShelfPersistenceAdapter.findById(1).onItem()
                .transformToUni { bookShelf -> Uni.createFrom().item(bookShelf?.id) }
        }, 1L)
    }

    @Test
    @RunOnVertxContext
    fun `assert that object with correct Id is created`(asserter: UniAsserter) {
        asserter.execute {
            val bookShelf = BookShelf()
            bookShelf.id = 1L
            Mockito.`when`(bookShelfRepository.persistAndFlush(anyOrNull())).thenReturn(Uni.createFrom().item(bookShelf))
        }
        asserter.assertNotNull() {
            bookShelfPersistenceAdapter.create(BookShelf())
        }
        asserter.assertEquals({
            bookShelfPersistenceAdapter.create(BookShelf()).onItem()
                .transformToUni { bookShelf -> Uni.createFrom().item(bookShelf.id) }
        }, 1L)
    }

    @Test
    @RunOnVertxContext
    fun `assert that NoSuchElementException is thrown if item does not exist`(asserter: UniAsserter) {
        asserter.execute {
            Mockito.`when`(bookShelfRepository.findById(anyOrNull())).thenReturn(Uni.createFrom().nullItem())
        }
        asserter.assertFailedWith({
            try {
                return@assertFailedWith bookShelfPersistenceAdapter.findById(1)
            } catch (e: Exception) {
                return@assertFailedWith Uni.createFrom().failure(e)
            }
        }, {t -> assertEquals(NoSuchElementException::class.java, t.javaClass) })
    }
}