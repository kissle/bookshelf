package secondbrain.kissle.bookshelf.adapter.out.persistence

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.application.port.out.CreateBookShelfPort
import secondbrain.kissle.bookshelf.application.port.out.LoadBookCopyPort
import secondbrain.kissle.bookshelf.application.port.out.LoadBookShelfPort
import secondbrain.kissle.bookshelf.domain.BookCopy
import secondbrain.kissle.bookshelf.domain.BookShelf


@ApplicationScoped
class BookShelfPersistenceAdapter(
    @Inject var bookShelfRepository: BookShelfRepository,
    @Inject var loadBookCopyPort: LoadBookCopyPort
): LoadBookShelfPort, CreateBookShelfPort {
    private val mapper = BookShelfMapper()

    override fun create(): Uni<BookShelf> {
        return bookShelfRepository.persistAndFlush(
            mapper.toEntity(null, mutableListOf())
        ).onItem().transformToUni { shelf ->
            Uni.createFrom().item(
            mapper.toDomain(shelf.id, mutableListOf())) }
    }

    override fun findAll(): Uni<List<BookShelf>> {
        return bookShelfRepository.listAll().onItem().transformToMulti { list ->
            Multi.createFrom().iterable(list) }
            .onItem().transformToUniAndMerge { shelf ->
                bookCopyIdsToBookCopies(shelf.bookCopyIds).onItem().transformToUni { list ->
                    Uni.createFrom().item(BookShelf(shelf.id, list))
                }
            }.collect().asList()
    }

    override fun findById(id: Long): Uni<BookShelf?> {
        return bookShelfRepository.findById(id).onItem().transformToUni { shelf ->
            if (shelf == null) {
                Uni.createFrom().nullItem()
            } else {
                bookCopyIdsToBookCopies(shelf.bookCopyIds).onItem().transformToUni { list ->
                    Uni.createFrom().item(BookShelf(shelf.id, list))
                }
            }
        }.onItem().ifNull().failWith(NoSuchElementException("Book shelf not found."))
    }

    private fun getBookCopy(id: Long): Uni<BookCopy> {
        return loadBookCopyPort.findById(id).onItem().transformToUni { copy ->
            Uni.createFrom().item(copy)
        }
    }

    private fun bookCopyIdsToBookCopies(ids: List<Long>): Uni<MutableList<BookCopy>> {
        return Multi.createFrom().iterable(ids).onItem().transformToUniAndMerge {
                id -> getBookCopy(id).onItem().transformToUni { copy ->
            Uni.createFrom().item(BookCopy(copy.id, copy.book, copy.mediumType))
        }
        }.collect().asList()
    }
}