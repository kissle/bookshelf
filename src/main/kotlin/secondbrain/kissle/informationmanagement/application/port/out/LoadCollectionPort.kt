package secondbrain.kissle.informationmanagement.application.port.out

import io.smallrye.mutiny.Uni
import secondbrain.kissle.informationmanagement.domain.InformationCollection

interface LoadCollectionPort {
    fun findById(id: Long): Uni<InformationCollection>
}