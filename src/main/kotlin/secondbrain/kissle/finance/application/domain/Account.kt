package secondbrain.kissle.finance.application.domain

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class Account private constructor(
    @Id
    val iban: String,

    @Relationship("OWNS", direction = Relationship.Direction.INCOMING)
    val owners: List<Owner>
) {
    protected constructor(): this("DE0815", listOf(Owner.create(null, "ACCOUNT_TEMPLATE_OWNER")))

    init {
        require(iban.isNotBlank()) { "IBAN cannot be blank" }
        require(owners.isNotEmpty()) { "There must be at least one owner" }
    }

    companion object {
        fun create(iban: String, owners: List<Owner>): Account {
            return Account(iban, owners)
        }
    }
}