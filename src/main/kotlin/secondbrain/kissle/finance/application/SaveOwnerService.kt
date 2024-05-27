package secondbrain.kissle.finance.application

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.finance.application.domain.Owner
import secondbrain.kissle.finance.application.port.`in`.web.SaveOwnerUseCase
import secondbrain.kissle.finance.application.port.out.SaveOwnerPort

@ApplicationScoped
class SaveOwnerService(
    @Inject
    private var saveOwnerPort: SaveOwnerPort
): SaveOwnerUseCase {

    override fun save(owner: String): Owner {
        return (saveOwnerPort.save(Owner(null, owner)))
    }
}