package secondbrain.kissle.informationmanagement.adapter.out.persitence

import secondbrain.kissle.informationmanagement.domain.InformationCollection

class InformationCollectionMapper {

    companion object {
        fun toEntity(informationCollection: InformationCollection): InformationCollectionEntity {
            if (informationCollection.id == null) {
                return InformationCollectionEntity.withoutId(informationCollection.name)
            }
            return InformationCollectionEntity.withId(informationCollection.id, informationCollection.name)
        }

        fun toDomain(informationCollectionEntity: InformationCollectionEntity): InformationCollection {
            return InformationCollection(informationCollectionEntity.id, informationCollectionEntity.name, false)
        }
    }
}