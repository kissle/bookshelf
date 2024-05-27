package secondbrain.kissle.finance.adapter.out.persistence.entity

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity("Owner")
class OwnerEntity {
    @Id
    @GeneratedValue
    var id: Long? = null
    lateinit var name: String

    companion object {
        fun create(id: Long?, name: String): OwnerEntity {
            return OwnerEntity().apply {
                this.name = name
                this.id = id
            }
        }
    }
}