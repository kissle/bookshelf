package secondbrain.kissle.finance.application.port.out

import secondbrain.kissle.finance.application.domain.Owner

interface SaveOwnerPort {
    fun save(owner: Owner): Owner
}