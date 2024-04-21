package secondbrain.kissle.informationmanagement.application.port.out

import io.smallrye.mutiny.Uni
import secondbrain.kissle.informationmanagement.domain.Component

interface LoadComponentsOfCollectionPort {
    fun loadComponents(id: Long): Uni<List<Component>>
}