package com.example.demo.boundaries.account.infrastructure.entity

import com.example.demo.boundaries.account.infrastructure.entity.AuditEntityBase
import javax.persistence.Entity

@Entity
class PersonEntity(
        val name: String
) : AuditEntityBase()