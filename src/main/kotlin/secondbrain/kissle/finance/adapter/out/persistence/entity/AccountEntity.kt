package secondbrain.kissle.finance.adapter.out.persistence.entity

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import secondbrain.kissle.finance.application.domain.Owner

@NodeEntity("Account")
class AccountEntity {
    @Relationship("OWNS", direction = Relationship.Direction.INCOMING)
    var owners: List<Owner> = emptyList()
    @Id
    var iban: String = ""

    companion object {
        fun create(iban: String, ownerEntities: List<Owner>): AccountEntity {
            return AccountEntity().apply {
                this.iban = iban
                this.owners = ownerEntities
            }
        }
    }
}