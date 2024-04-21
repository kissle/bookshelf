package secondbrain.kissle.informationmanagement.application.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.kissle.informationmanagement.domain.Component

interface LoadComponentsOfCollectionUseCase {
    fun loadComponents(id: Long): Uni<List<Component>>
}