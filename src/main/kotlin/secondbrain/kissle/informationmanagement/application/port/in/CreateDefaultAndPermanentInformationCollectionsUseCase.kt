package secondbrain.kissle.informationmanagement.application.port.`in`

import io.smallrye.mutiny.Uni

interface CreateDefaultAndPermanentInformationCollectionsUseCase {
    fun createDefault(): Uni<Void>
}