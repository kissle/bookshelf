package secondbrain.kissle.informationmanagement.application.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.kissle.informationmanagement.domain.InformationCollection

interface CreateDefaultAndPermanentInformationCollectionsUseCase {
    fun createDefault(): Uni<List<InformationCollection>>
}