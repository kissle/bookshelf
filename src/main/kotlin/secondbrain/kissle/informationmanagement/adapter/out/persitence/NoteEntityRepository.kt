package secondbrain.kissle.informationmanagement.adapter.out.persitence

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class NoteEntityRepository: PanacheRepository<NoteEntity>