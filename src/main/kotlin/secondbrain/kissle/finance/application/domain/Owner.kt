package secondbrain.kissle.finance.application.domain

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity("Owner")
class Owner private constructor(
    @Id
    @GeneratedValue
    val id: Long?,
    val name: String
) {
    companion object {
        fun create(id: Long?, name: String) = Owner(id, name)
    }

    protected constructor() : this(null, "")
}
