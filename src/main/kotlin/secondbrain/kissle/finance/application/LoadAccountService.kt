package secondbrain.kissle.finance.application

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.port.`in`.web.LoadAccountUseCase
import secondbrain.kissle.finance.application.port.out.LoadAccountPort

@ApplicationScoped
class LoadAccountService: LoadAccountUseCase {

    @Inject
    private lateinit var loadAccountPort: LoadAccountPort

    override fun load(iban: String): Account {
        return loadAccountPort.load(iban)
    }
}