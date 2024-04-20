package secondbrain.kissle.informationmanagement.adapter.`in`.web

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import secondbrain.kissle.informationmanagement.application.port.`in`.AddNewCollectionToCollectionUseCase
import secondbrain.kissle.informationmanagement.application.port.`in`.CreateDefaultAndPermanentInformationCollectionsUseCase
import secondbrain.kissle.informationmanagement.domain.InformationCollection

@Path("/para/")
class InformationCollectionResource(
    @Inject
    private var createDefaultAndPermanentInformationCollectionsUseCase: CreateDefaultAndPermanentInformationCollectionsUseCase,
    @Inject
    private var addNewCollectionToCollectionUseCase: AddNewCollectionToCollectionUseCase
) {

    @GET
    @Path("/create-default")
    @WithTransaction
    fun createDefault(): Uni<Void> {
        return createDefaultAndPermanentInformationCollectionsUseCase.createDefault()
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @WithTransaction
    fun addNewCollection(@PathParam("id") id: Long, name: String): Uni<InformationCollection> {
        return addNewCollectionToCollectionUseCase.addCollection(id, name)
    }
}