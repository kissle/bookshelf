package secondbrain.kissle.bookshelf.adapter.`in`.web

import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import secondbrain.kissle.bookshelf.domain.BookShelf
import secondbrain.kissle.bookshelf.application.port.`in`.BookCopyDto
import secondbrain.kissle.bookshelf.application.port.`in`.BuyBookCopyUseCase
import secondbrain.kissle.bookshelf.application.port.`in`.CreateBookShelfUseCase
import secondbrain.kissle.bookshelf.application.port.`in`.LoadBookShelfUseCase

@Path("/bookshelf")
class BookShelfResource {

    @Inject
    lateinit var loadBookShelfUseCase: LoadBookShelfUseCase

    @Inject
    lateinit var createBookShelfUseCase: CreateBookShelfUseCase

    @Inject
    lateinit var buyBookCopyUseCase: BuyBookCopyUseCase

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

    @POST
    @Path("/{id}/add_book")
    fun addBook(@PathParam("id") id: Long, copy: BookCopyDto): Uni<BookShelf> {
        return buyBookCopyUseCase.addBookCopyToDefaultShelf(id, copy)
    }
}