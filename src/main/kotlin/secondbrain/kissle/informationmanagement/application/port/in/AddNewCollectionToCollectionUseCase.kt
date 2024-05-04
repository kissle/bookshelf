package secondbrain.kissle.informationmanagement.application.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.kissle.informationmanagement.domain.InformationCollection

interface AddNewCollectionToCollectionUseCase {
    fun addCollection(id: Long, name: String): Uni<InformationCollection>
}