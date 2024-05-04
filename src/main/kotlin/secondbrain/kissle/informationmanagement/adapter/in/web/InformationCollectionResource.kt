package secondbrain.kissle.informationmanagement.adapter.`in`.web

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import secondbrain.kissle.informationmanagement.adapter.out.persitence.InformationCollectionPersistenceAdapter
import secondbrain.kissle.informationmanagement.application.port.`in`.AddNewComponentToCollectionUseCase
import secondbrain.kissle.informationmanagement.application.port.`in`.CreateDefaultAndPermanentInformationCollectionsUseCase
import secondbrain.kissle.informationmanagement.application.port.`in`.LoadComponentsOfCollectionUseCase
import secondbrain.kissle.informationmanagement.domain.Component
import secondbrain.kissle.informationmanagement.domain.InformationCollection

@Path("/para/")
class InformationCollectionResource(
    @Inject
    private var createDefaultAndPermanentInformationCollectionsUseCase: CreateDefaultAndPermanentInformationCollectionsUseCase,
    @Inject private var loadComponentsOfCollectionUseCase: LoadComponentsOfCollectionUseCase,
    @Inject private var informationCollectionPersistenceAdapter: InformationCollectionPersistenceAdapter,
    @Inject private var addNewComponentToCollectionUseCase: AddNewComponentToCollectionUseCase
) {


    @GET
    @Path("/create-default")
    @WithTransaction
    fun createDefault(): Uni<Void> {
        return createDefaultAndPermanentInformationCollectionsUseCase.createDefault()
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun addNewComponentToCollection(@PathParam("id") id: Long, component: ComponentDto): Uni<InformationCollection> {
        return addNewComponentToCollectionUseCase.addComponent(id, component)
    }

    @GET
    @Path("/{id}")
    fun findById(@PathParam("id") id: Long): Uni<InformationCollection> {
        return informationCollectionPersistenceAdapter.findById(id)
    }

    @GET
    @Path("/{id}/elements")
    @Produces(MediaType.APPLICATION_JSON)
    fun loadAllComponents(@PathParam("id") id: Long): Uni<List<Component>> {
        return loadComponentsOfCollectionUseCase.loadComponents(id)
    }
}