package br.com.eprogramar.ebank.account.crosscutting.entity

import br.com.eprogramar.ebank.account.crosscutting.ActivityType
import java.math.BigDecimal
import javax.persistence.*

@Embeddable
class Activity(var value: BigDecimal, var type: ActivityType)