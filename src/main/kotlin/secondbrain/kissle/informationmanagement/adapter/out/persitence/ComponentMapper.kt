package secondbrain.kissle.informationmanagement.adapter.out.persitence

import secondbrain.kissle.informationmanagement.domain.Component

class ComponentMapper {

    fun toEntity(component: Component): ComponentEntity {
        if (component.id == null) {
            throw IllegalArgumentException("Component id must not be null")
        }
        val entity = ComponentEntity()
        entity.componentId = component.id!!
        entity.componentType = component.getComponentType()
        return entity
    }
}