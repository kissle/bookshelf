package secondbrain.kissle.neo4j

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity
class BookNode {

    @Id
    var title: String? = null
}