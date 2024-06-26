package secondbrain.kissle.bookshelf.adapter.`in`.web

import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import secondbrain.kissle.bookshelf.domain.Book
import secondbrain.kissle.bookshelf.application.port.`in`.CreateBookUseCase
import secondbrain.kissle.bookshelf.application.port.`in`.LoadBookUseCase

@Path("/books")
class BookResource {

    @Inject
    lateinit var loadBookUseCase: LoadBookUseCase

    @Inject
    lateinit var createBookUseCase: CreateBookUseCase

    @GET
    @WithSession
    fun findAll(): Uni<List<Book>> {
        return loadBookUseCase.findAll()
    }

    @GET
    @Path("/{id}")
    @WithSession
    fun findById(@PathParam("id") id: Long): Uni<Book?> {
        return loadBookUseCase.findById(id)
    }

    @POST
    @WithTransaction
    fun create(book: Book): Uni<Book> {
        return createBookUseCase.create(book)
    }
}