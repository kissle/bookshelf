package secondbrain.kissle.bookshelf.application

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.adapter.out.persistence.BookShelfRepository
import secondbrain.kissle.bookshelf.domain.BookShelf
import secondbrain.kissle.bookshelf.port.`in`.CreateBookShelfUseCase

@ApplicationScoped
class CreateBookShelfService: CreateBookShelfUseCase {

    @Inject
    private lateinit var repository: BookShelfRepository

    @WithTransaction
    override fun create(): Uni<BookShelf> {
        return repository.create(BookShelf())
    }
}