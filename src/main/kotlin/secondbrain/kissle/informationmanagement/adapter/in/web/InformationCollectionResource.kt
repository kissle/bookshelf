package secondbrain.kissle.informationmanagement.adapter.`in`.web

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import secondbrain.kissle.informationmanagement.application.port.`in`.AddNewCollectionToCollectionUseCase
import secondbrain.kissle.informationmanagement.application.port.`in`.AddNewNoteToCollectionUseCase
import secondbrain.kissle.informationmanagement.application.port.`in`.CreateDefaultAndPermanentInformationCollectionsUseCase
import secondbrain.kissle.informationmanagement.domain.InformationCollection

@Path("/para/")
class InformationCollectionResource(
    @Inject
    private var createDefaultAndPermanentInformationCollectionsUseCase: CreateDefaultAndPermanentInformationCollectionsUseCase,
    @Inject
    private var addNewCollectionToCollectionUseCase: AddNewCollectionToCollectionUseCase,
    @Inject
    private var addNewNoteToCollectionUseCase: AddNewNoteToCollectionUseCase
) {

    private val noteDtoMapper = NoteDtoMapper()

    @GET
    @Path("/create-default")
    @WithTransaction
    fun createDefault(): Uni<Void> {
        return createDefaultAndPermanentInformationCollectionsUseCase.createDefault()
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @WithTransaction
    fun addNewCollection(@PathParam("id") id: Long, component: ComponentDto): Uni<InformationCollection> {
        return when (component) {
            is NoteDto -> addNewNoteToCollectionUseCase.addNote(id, noteDtoMapper.toDomain(component))
            is InformationCollectionDto -> addNewCollectionToCollectionUseCase.addCollection(id, component.name)
            else -> throw IllegalArgumentException("Unknown component type")
        }
    }
}