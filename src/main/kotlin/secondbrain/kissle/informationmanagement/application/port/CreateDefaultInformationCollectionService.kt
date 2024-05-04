package secondbrain.kissle.informationmanagement.application.port

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.informationmanagement.application.port.`in`.CreateDefaultAndPermanentInformationCollectionsUseCase
import secondbrain.kissle.informationmanagement.application.port.out.CreateInformationCollectionPort
import secondbrain.kissle.informationmanagement.domain.InformationCollection
import secondbrain.kissle.informationmanagement.domain.PARAEnum

@ApplicationScoped
class CreateDefaultInformationCollectionService(
    @Inject
    private var createInformationCollectionPort: CreateInformationCollectionPort
): CreateDefaultAndPermanentInformationCollectionsUseCase {
    override fun createDefault(): Uni<Void> {
        val collections = PARAEnum.entries.map {
            InformationCollection(
                id = null,
                name = it.label,
                isPermanent = true
            )
        }
        return createInformationCollectionPort.create(collections)
    }
}