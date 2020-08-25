package br.com.eprogramar.ebank.account.crosscutting.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
data class AccountEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val person: Person,
        var balance: BigDecimal,
        val accountNumber: Int,
        @ElementCollection
        var activities: MutableList<Activity> = mutableListOf(),

        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        var createdAt: Date? = null,
        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        var modifiedAt: Date? = null
)

@Embeddable
class Person(var name: String)