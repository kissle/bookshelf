package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.application.port.out.CreateBookCopyPort
import secondbrain.kissle.bookshelf.application.port.out.CreateBookPort
import secondbrain.kissle.bookshelf.application.port.out.LoadBookCopyPort
import secondbrain.kissle.bookshelf.application.port.out.LoadBookPort
import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.domain.BookCopy

@ApplicationScoped
class BookCopyPersistenceAdapter(
   @Inject private var bookCopyRepository: BookCopyRepository,
   @Inject var loadBookPort: LoadBookPort,
   @Inject var createBookPort: CreateBookPort
): LoadBookCopyPort, CreateBookCopyPort {

    private val mapper = BookCopyMapper()

    override fun create(bookCopy: BookCopy): Uni<BookCopy> {
        val bookUni: Uni<Book> = getOrCreateBook(bookCopy.book)

        return bookUni.onItem().transformToUni { book ->
            val entity = mapper.toEntity(bookCopy.id, book.id!!, bookCopy.mediumType)
            bookCopyRepository.persistAndFlush(entity)
                .onItem()
                .transformToUni {copy -> Uni.createFrom().item(mapper.toDomain(copy.id, book, copy.type))}
        }
    }

    override fun findAll(): Uni<List<BookCopy>> {
        return bookCopyRepository.listAll().onItem().transformToMulti { list ->
            Multi.createFrom().iterable(list) }.onItem().transformToUniAndMerge { copy ->
            getBook(copy.bookId ?: 0).onItem().transform { book ->
                mapper.toDomain(copy.id, book, copy.type)
            }
        }.collect().asList()
    }

    override fun findById(id: Long): Uni<BookCopy> {
        return bookCopyRepository.findById(id).onItem().transformToUni {
            copy ->
            if (copy == null) {
                Uni.createFrom().nullItem()
            } else {
                loadBookPort.findById(copy.id ?: 0)
                    .onItem().transformToUni { book ->
                        if (book == null) {
                            Uni.createFrom().nullItem()
                        } else {
                            Uni.createFrom().item(mapper.toDomain(copy.id, book, copy.type) )
                        }

                    }
            }
        }.onItem().ifNull().failWith { NoSuchElementException("Book Copy could not be restored.") }
    }

    private fun getOrCreateBook(book: Book): Uni<Book> {
        return if (book.id == null) {
            createBookPort.create(book)
                .onItem()
                .transformToUni { item -> Uni.createFrom().item(item) }
        } else {
            getBook(book.id ?: 0)
        }
    }

    private fun getBook(id: Long): Uni<Book> {
        return loadBookPort.findById(id)
            .onItem().transformToUni { item -> Uni.createFrom().item(item) }
    }
}