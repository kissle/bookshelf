package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.application.port.out.CreateBookPort
import secondbrain.kissle.bookshelf.application.port.out.LoadBookPort
import secondbrain.kissle.bookshelf.domain.Book

@ApplicationScoped
class BookPersistenceAdapter(
    @Inject var bookRepository: BookRepository
): LoadBookPort, CreateBookPort {

    val mapper = BookMapper()

    override fun create(book: Book): Uni<Book> {
        return bookRepository.persistAndFlush(mapper.toEntity(book)).onItem().transformToUni { bookEntity ->
            Uni.createFrom().item(mapper.toDomain(bookEntity))}
    }

    override fun findAll(): Uni<List<Book>> {
        return bookRepository.listAll().onItem().transformToUni { bookEntities ->
            Uni.createFrom().item(bookEntities.map { mapper.toDomain(it) })
        }
    }

    override fun findById(id: Long): Uni<Book?> {
        return bookRepository.findById(id)
            .onItem()
            .transformToUni { bookEntity ->
                if (bookEntity != null) {
                    Uni.createFrom().item(mapper.toDomain(bookEntity))
                } else {
                    Uni.createFrom().failure { NoSuchElementException("Book not found") }
                }
            }

    }
}