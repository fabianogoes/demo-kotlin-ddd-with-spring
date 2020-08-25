package br.com.eprogramar.ebank.account.crosscutting.entity

import br.com.eprogramar.ebank.account.crosscutting.entity.AuditEntityBase
import javax.persistence.Entity

@Entity
class PersonEntity(
        val name: String
) : AuditEntityBase()