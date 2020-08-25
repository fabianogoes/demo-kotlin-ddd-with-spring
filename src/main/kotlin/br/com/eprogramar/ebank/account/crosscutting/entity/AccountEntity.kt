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
        @ManyToOne(cascade = [CascadeType.ALL])
        val person: PersonEntity,
        var balance: BigDecimal,
        val accountNumber: Int,
        @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var activities: MutableList<ActivityEntity> = mutableListOf(),

        @CreatedDate
        @Temporal(TemporalType.TIMESTAMP)
        var createdAt: Date? = null,
        @LastModifiedDate
        @Temporal(TemporalType.TIMESTAMP)
        var modifiedAt: Date? = null
)