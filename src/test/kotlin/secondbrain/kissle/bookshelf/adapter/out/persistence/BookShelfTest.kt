package secondbrain.kissle.bookshelf.adapter.out.persistence

import org.junit.jupiter.api.Test

class BookShelfTest {

    @Test
    fun `should generate Object with id equals null`() {
        val bookShelf = BookShelfEntity.withoutId(listOf(1L))
        assert(bookShelf.id == null)
        assert(bookShelf.bookCopyIds == listOf(1L))
    }

    @Test
    fun `should generate Object with id equals 1L`() {
        val bookShelf = BookShelfEntity.withId(1L, listOf(1L))
        assert(bookShelf.id == 1L)
        assert(bookShelf.bookCopyIds == listOf(1L))
    }
}