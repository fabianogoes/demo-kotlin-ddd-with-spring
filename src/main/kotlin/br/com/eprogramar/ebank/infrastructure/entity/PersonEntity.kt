package br.com.eprogramar.ebank.infrastructure.entity

import javax.persistence.Entity

@Entity
class PersonEntity(
        val name: String
) : AuditEntityBase()