package secondbrain.kissle.bookshelf.adapter.out.persistence

import secondbrain.kissle.bookshelf.domain.Book

class BookMapper {

    fun toEntity(book: Book): BookEntity {
        if (book.id == null) {
            return BookEntity.withoutId(book.title, book.subTitle)
        }
        return BookEntity.withId(book.id!!, book.title, book.subTitle)
    }

    fun toDomain(bookEntity: BookEntity): Book {
        return Book(bookEntity.id, bookEntity.title, bookEntity.subTitle)
    }
}