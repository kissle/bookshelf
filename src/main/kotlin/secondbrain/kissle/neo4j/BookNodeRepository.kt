package secondbrain.kissle.neo4j

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import java.util.Map

@ApplicationScoped
class BookNodeRepository {

    @Inject
    lateinit var sessionFactory: SessionFactory

    fun create(book: BookNode) {
        val session = sessionFactory.openSession()
        return session.save(book)
    }

//    fun fetchMovieGraph(): MovieGraph {
//        val session = sessionFactory!!.openSession()
//        val result = session.query(
//            " MATCH (m:Movie) <- [r:ACTED_IN] - (p:Person)"
//                    + " WITH m, p ORDER BY m.title, p.name"
//                    + " RETURN m.title AS movie, collect(p.name) AS actors",
//            Map.of<String, Any?>(), true
//        )
//
//        return MovieGraph(result)
//    }
}