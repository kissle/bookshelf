package secondbrain.kissle.informationmanagement.application.port

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.informationmanagement.application.port.`in`.AddNewCollectionToCollectionUseCase
import secondbrain.kissle.informationmanagement.application.port.out.CreateInformationCollectionPort
import secondbrain.kissle.informationmanagement.application.port.out.LoadCollectionPort
import secondbrain.kissle.informationmanagement.application.port.out.UpdateInformationCollectionPort
import secondbrain.kissle.informationmanagement.domain.InformationCollection

@ApplicationScoped
class AddNewCollectionToCollectionService(
    @Inject
    private var loadCollectionPort: LoadCollectionPort,
    @Inject
    private var createInformationCollectionPort: CreateInformationCollectionPort,
    @Inject
    private var updateInformationCollectionPort: UpdateInformationCollectionPort
): AddNewCollectionToCollectionUseCase {

//    override fun addCollection(id: Long, name: String): Uni<InformationCollection> {
//        val collection = InformationCollection(id, "", false)
//        val newCollection = InformationCollection(null, name, false)
//        collection.addElement(newCollection)
//        return updateInformationCollectionPort.update(collection)
//    }

    override fun addCollection(id: Long, name: String): Uni<InformationCollection> {
        return createNewCollection(name)
            .onItem().transformToUni { newCollection ->
            loadCollectionPort.findById(id).onItem().transformToUni { collection ->
                collection.addElement(newCollection)
                updateCollection(collection)
            }
        }

    }

//    override fun addCollection(id: Long, name: String): Uni<InformationCollection> {
//        return loadCollectionPort.findById(id)
//            .onItem().transformToUni { collection ->
//                createNewCollection(name).onItem().transformToUni { newCollection ->
//                    collection.addElement(newCollection)
//                    println(newCollection.id)
//                    Uni.createFrom().item(collection)
//                }
//            }.onItem().transformToUni { collection ->
//                updateCollection(collection)
//            }
//
//    }

    private fun createNewCollection(name: String): Uni<InformationCollection> {
        val newCollection = InformationCollection(null, name, false)
        return createInformationCollectionPort.createInformationCollection(newCollection)
    }

    private fun updateCollection(collection: InformationCollection): Uni<InformationCollection> {
        return updateInformationCollectionPort.update(collection)
    }
}