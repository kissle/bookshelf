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
    override fun createDefault(): Uni<List<InformationCollection>> {
        val unis = PARAEnum.entries.map {
            val informationCollection = InformationCollection(
                id = null,
                name = it.label,
                isPermanent = true
            )
            createInformationCollectionPort.createInformationCollection(informationCollection)
        }
        return Uni.join().all(unis).andCollectFailures()
            .onItem().transformToUni { items -> Uni.createFrom().item(items) }
    }
}