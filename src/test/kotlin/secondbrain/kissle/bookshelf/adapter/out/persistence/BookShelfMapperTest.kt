package secondbrain.kissle.bookshelf.adapter.out.persistence

import org.junit.jupiter.api.Test
import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.domain.BookCopy
import secondbrain.kissle.bookshelf.domain.BookShelf
import secondbrain.kissle.bookshelf.domain.MediumType

class BookShelfMapperTest {

    @Test
    fun `should map from entity to domain`() {
        val copyEntity = BookCopyEntity.withId(1L, 1L, MediumType.HARDCOPY)
        val copy = BookCopy(1L, Book(1L, "title", "subTitle"), MediumType.HARDCOPY)
        val bookShelfEntity = BookShelfEntity.withId(1L, listOf(copyEntity.id!!))
        val mapper = BookShelfMapper()
        val bookShelf = mapper.toDomain(bookShelfEntity.id, listOf(copy))

        assert(bookShelf.javaClass == BookShelf::class.java)
        assert(bookShelf.id == 1L)
        assert(bookShelf.bookCopies.size == 1)
        assert(bookShelf.bookCopies[0] == copy)
    }

    @Test
    fun `should map from domain to entity`() {
        val copy = BookCopy(1L, Book(1L, "title", "subTitle"), MediumType.HARDCOPY)
        val bookShelf = BookShelf(1L, mutableListOf(copy))
        val mapper = BookShelfMapper()
        val bookShelfEntity = mapper.toEntity(bookShelf.id, bookShelf.bookCopies.map { it.id!! })

        assert(bookShelfEntity.javaClass == BookShelfEntity::class.java)
        assert(bookShelfEntity.id == 1L)
        assert(bookShelfEntity.bookCopyIds.size == 1)
        assert(bookShelfEntity.bookCopyIds[0] == copy.id)
    }
}