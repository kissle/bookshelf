package secondbrain.kissle.bookshelf.application

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.bookshelf.adapter.out.persistence.BookShelfRepository
import secondbrain.kissle.bookshelf.domain.BookShelf
import secondbrain.kissle.bookshelf.application.port.`in`.CreateBookShelfUseCase
import secondbrain.kissle.bookshelf.application.port.out.CreateBookShelfPort

@ApplicationScoped
class CreateBookShelfService: CreateBookShelfUseCase {

    @Inject
    private lateinit var repository: BookShelfRepository

    override fun create(): Uni<BookShelf> {
       return repository.persist(BookShelf())
    }
}