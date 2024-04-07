package secondbrain.kissle.bookshelf.adapter.out.persistence

import org.junit.jupiter.api.Test
import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.domain.BookCopy
import secondbrain.kissle.bookshelf.domain.MediumType

class BookCopyMapperTest {

    @Test
    fun `should map from entity to domain`() {
        val bookCopyEntity = BookCopyEntity.withId(1L, 1L, MediumType.HARDCOPY)
        val mapper = BookCopyMapper()
        val book = Book(1L, "title", "subtitle")
        val bookCopy = mapper.toDomain(bookCopyEntity.id, book, bookCopyEntity.type)

        assert(bookCopy.javaClass == BookCopy::class.java)
        assert(bookCopy.id == 1L)
        assert(bookCopy.book == book)
        assert(bookCopy.mediumType == MediumType.HARDCOPY)
    }

    @Test
    fun `should map from domain to entity`() {
        val book = Book(1L, "title", "subtitle")
        val bookCopy = BookCopy(1L, book, MediumType.HARDCOPY)
        val mapper = BookCopyMapper()
        val bookCopyEntity = mapper.toEntity(bookCopy.id, book.id!!, bookCopy.mediumType)

        assert(bookCopyEntity.javaClass == BookCopyEntity::class.java)
        assert(bookCopyEntity.id == 1L)
        assert(bookCopyEntity.bookId == book.id)
        assert(bookCopyEntity.type == MediumType.HARDCOPY)
    }
}