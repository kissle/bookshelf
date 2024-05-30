package secondbrain.kissle.finance.application.port.`in`.web

import secondbrain.kissle.finance.application.domain.Owner

interface SaveOwnerUseCase {
    fun save(owner: Owner): Owner
}