package secondbrain.bookshelf.application

import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.bookshelf.adapter.out.persistence.BookShelfRepository
import secondbrain.bookshelf.domain.BookShelf
import secondbrain.bookshelf.port.`in`.LoadBookShelfUseCase

@ApplicationScoped
class LoadBookShelfService:
    LoadBookShelfUseCase {

    @Inject
    private lateinit var repository: BookShelfRepository

    @WithSession
    override fun findAll(): Uni<List<BookShelf>> {
        return repository.findAll()
    }

    @WithSession
    override fun findById(id: Long): Uni<BookShelf?> {
        return repository.findById(id)
    }
}