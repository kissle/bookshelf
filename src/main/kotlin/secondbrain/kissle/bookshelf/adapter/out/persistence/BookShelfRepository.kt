package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import secondbrain.kissle.bookshelf.domain.BookShelf
import secondbrain.kissle.bookshelf.port.out.CreateBookShelfPort
import secondbrain.kissle.bookshelf.port.out.LoadBookShelfPort
import secondbrain.kissle.bookshelf.port.out.UpdateBookShelfPort


@ApplicationScoped
class BookShelfRepository: LoadBookShelfPort,
    CreateBookShelfPort, UpdateBookShelfPort {

    override fun create(bookShelf: BookShelf): Uni<BookShelf> {
        return bookShelf.persistAndFlush<BookShelf>().onItem().ifNull()
            .failWith(IllegalStateException("BookShelf could not be created"))
    }

    override fun findAll(): Uni<List<BookShelf>> {
        return BookShelf.findAll().list()
    }

    override fun findById(id: Long): Uni<BookShelf?> {
        return BookShelf.findById(id)
    }

    override fun getDefault(): Uni<BookShelf> {
        val firstItemUni: Uni<BookShelf?> = BookShelf.findAll().firstResult()

        return firstItemUni.onItem().transformToUni { item: BookShelf? ->
            if (item == null) {
                create(BookShelf())
            } else {
                Uni.createFrom().item(item)
            }
        }
    }

    override fun update(bookShelf: BookShelf): Uni<BookShelf> {
        return bookShelf.persistAndFlush()
    }
}