package secondbrain.kissle.finance.adapter.out.persistence.entity

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity("Account")
class AccountEntity {
    @Relationship("OWNES", direction = Relationship.Direction.INCOMING)
    lateinit var owners: List<OwnerEntity>
    @Id
    var iban: String = ""

    companion object {
        fun create(iban: String, ownerEntities: List<OwnerEntity>): AccountEntity {
            return AccountEntity().apply {
                this.iban = iban
                this.owners = ownerEntities
            }
        }
    }
}