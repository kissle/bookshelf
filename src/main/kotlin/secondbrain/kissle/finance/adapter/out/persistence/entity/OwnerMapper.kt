package secondbrain.kissle.finance.adapter.out.persistence.entity

import secondbrain.kissle.finance.application.domain.Owner

class OwnerMapper {

    companion object {
        fun toDomain(ownerEntity: OwnerEntity): Owner {
            return Owner(ownerEntity.id, ownerEntity.name)
        }

        fun toEntity(owner: Owner): OwnerEntity {
            return OwnerEntity.create(owner.id, owner.name)
        }
    }
}