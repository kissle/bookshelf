package secondbrain.kissle.finance.application.port.out

import secondbrain.kissle.finance.application.domain.Owner

interface LoadOwnerPort {
    fun findByName(name: String): Owner
}