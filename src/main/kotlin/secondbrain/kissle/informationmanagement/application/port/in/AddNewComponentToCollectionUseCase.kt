package secondbrain.kissle.informationmanagement.application.port.`in`

import io.smallrye.mutiny.Uni
import secondbrain.kissle.informationmanagement.adapter.`in`.web.ComponentDto
import secondbrain.kissle.informationmanagement.domain.InformationCollection

interface AddNewComponentToCollectionUseCase {
    fun addComponent(id: Long, component: ComponentDto): Uni<InformationCollection>
}