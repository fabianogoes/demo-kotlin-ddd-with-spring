package com.example.demo.boundaries.account.infrastructure

import javax.persistence.Entity

@Entity
class Person(
        val name: String
) : AuditBase()