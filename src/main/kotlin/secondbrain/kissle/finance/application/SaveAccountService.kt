package secondbrain.kissle.finance.application

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.port.`in`.web.SaveAccountUseCase
import secondbrain.kissle.finance.application.port.out.SaveAccountPort

@ApplicationScoped
class SaveAccountService(
    @Inject private var saveAccountPort: SaveAccountPort
): SaveAccountUseCase {
    override fun save(account: Account): Account {
        return saveAccountPort.save(account)
    }
}