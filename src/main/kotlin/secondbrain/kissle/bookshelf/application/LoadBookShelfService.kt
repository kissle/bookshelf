package secondbrain.kissle.bookshelf.application

import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.adapter.out.persistence.BookShelfRepository
import secondbrain.kissle.bookshelf.domain.BookShelf
import secondbrain.kissle.bookshelf.application.port.`in`.LoadBookShelfUseCase

@ApplicationScoped
class LoadBookShelfService: LoadBookShelfUseCase {

    @Inject
    private lateinit var repository: BookShelfRepository

    @WithSession
    override fun findAll(): Uni<List<BookShelf>> {
        return repository.listAll()
    }

    @WithSession
    override fun findById(id: Long): Uni<BookShelf?> {
        return repository.findById(id).onItem().ifNull().failWith { NoSuchElementException("BookShelf not found") }
    }
}