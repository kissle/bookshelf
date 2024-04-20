package secondbrain.kissle.informationmanagement.adapter.out.persitence

import jakarta.persistence.Embeddable
import secondbrain.kissle.informationmanagement.domain.ComponentTypes

@Embeddable
class ComponentEntity {
    var componentId: Long = 0L
    var componentType: ComponentTypes = ComponentTypes.NOTE
}