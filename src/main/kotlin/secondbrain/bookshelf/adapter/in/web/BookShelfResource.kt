package secondbrain.bookshelf.adapter.`in`.web

import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import secondbrain.bookshelf.domain.BookShelf
import secondbrain.bookshelf.port.`in`.CreateBookShelfUseCase
import secondbrain.bookshelf.port.`in`.LoadBookShelfUseCase

@Path("/bookshelf")
class BookShelfResource {

    @Inject
    lateinit var loadBookShelfUseCase: LoadBookShelfUseCase

    @Inject
    lateinit var createBookShelfUseCase: CreateBookShelfUseCase

    @GET
    fun findAll(): Uni<List<BookShelf>> {
        return loadBookShelfUseCase.findAll()
    }

    @GET
    @Path("/{id}")
    fun findById(@PathParam("id") id: Long): Uni<BookShelf?> {
        return loadBookShelfUseCase.findById(id)
    }

    @POST
    fun create(): Uni<BookShelf> {
        return createBookShelfUseCase.create()
    }
}