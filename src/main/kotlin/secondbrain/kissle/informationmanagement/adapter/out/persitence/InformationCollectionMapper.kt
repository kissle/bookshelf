package secondbrain.kissle.informationmanagement.adapter.out.persitence

import secondbrain.kissle.informationmanagement.domain.Component
import secondbrain.kissle.informationmanagement.domain.InformationCollection

class InformationCollectionMapper {

    companion object {
        fun toEntity(informationCollection: InformationCollection): InformationCollectionEntity {
            if (informationCollection.id == null) {
                return InformationCollectionEntity.withoutId(informationCollection.name)
            }
            val elements = informationCollection.elements.map { ComponentMapper().toEntity(it) }.toMutableList()
            return InformationCollectionEntity.withId(informationCollection.id, informationCollection.name, elements)
        }

        fun toDomain(informationCollectionEntity: InformationCollectionEntity, elements: List<Component>): InformationCollection {
            val collection = InformationCollection(informationCollectionEntity.id, informationCollectionEntity.name, false)
            collection.elements.addAll(elements.toMutableList())
            return collection
        }
    }
}