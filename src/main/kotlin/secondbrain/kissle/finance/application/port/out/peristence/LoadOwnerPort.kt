package secondbrain.kissle.finance.application.port.out.peristence

import secondbrain.kissle.finance.application.domain.Owner

interface LoadOwnerPort {
    fun findByName(name: String): Owner
}