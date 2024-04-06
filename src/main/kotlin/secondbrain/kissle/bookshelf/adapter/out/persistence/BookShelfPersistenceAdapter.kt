package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.application.port.out.CreateBookShelfPort
import secondbrain.kissle.bookshelf.application.port.out.LoadBookShelfPort
import secondbrain.kissle.bookshelf.domain.BookShelf


@ApplicationScoped
class BookShelfPersistenceAdapter(
    @Inject var bookShelfRepository: BookShelfRepository
): LoadBookShelfPort, CreateBookShelfPort {
    override fun create(bookShelf: BookShelf): Uni<BookShelf> {
        return bookShelfRepository.persistAndFlush(bookShelf)
    }

    override fun findAll(): Uni<List<BookShelf>> {
        return bookShelfRepository.listAll()
    }

    override fun findById(id: Long): Uni<BookShelf?> {
        return bookShelfRepository.findById(id)
            .onItem()
            .ifNull()
            .failWith { NoSuchElementException("BookShelf not found") }
    }
}