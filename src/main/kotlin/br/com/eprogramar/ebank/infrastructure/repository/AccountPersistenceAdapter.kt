package br.com.eprogramar.ebank.infrastructure.repository

import br.com.eprogramar.ebank.crosscutting.ActivityType
import br.com.eprogramar.ebank.domain.exception.AccountNotFoundException
import br.com.eprogramar.ebank.domain.persistence.AccountPersistencePort
import br.com.eprogramar.ebank.domain.vo.AccountVO
import br.com.eprogramar.ebank.domain.vo.ActivityVO
import br.com.eprogramar.ebank.infrastructure.entity.AccountEntity
import br.com.eprogramar.ebank.infrastructure.entity.ActivityEntity
import br.com.eprogramar.ebank.infrastructure.entity.PersonEntity
import org.springframework.stereotype.Repository

@Repository
internal class AccountPersistenceAdapter(val accountRepository: AccountRepository) : AccountPersistencePort {

    override fun persisNewAccount(accountVo: AccountVO) {
        val account: AccountEntity? = this.toNewAccount(accountVo)
        account?.let { accountRepository.save(it) }
    }

    override fun retrieveAllActivities(number: String): List<ActivityVO> {
        return accountRepository.findByNumber(number)
                .orElseThrow { throw AccountNotFoundException("Account Not Found") }
                .activities.map { activity -> ActivityVO(value = activity.value, type = activity.type.name) }
    }

    override fun retrieveAccountByNumber(accountNumber: String): AccountVO {
        val account = accountRepository.findByNumber(accountNumber)
                .orElseThrow { throw AccountNotFoundException("Account Not Found") }
        return this.toAccountVO(account)
    }

    override fun persistNewActivityAndUpdateAmount(accountVO: AccountVO, activityVO: ActivityVO) {
        val accountEntity = accountRepository.findByNumber(accountVO.number)
                .orElseThrow{ throw AccountNotFoundException("Account Not Found") }
        accountEntity.amount = accountVO.amount
        val newActivity: ActivityEntity = mapperToNewActivity(activityVO, accountEntity)
        accountEntity.activities.add(newActivity)
        accountRepository.save(accountEntity)
    }

    override fun retrieveAllAccount(): List<AccountVO> {
        return accountRepository.findAll().map { this.toAccountVO(it) }
    }

    override fun generateNextAccountNumber(): Int {
        return accountRepository.findAll()
                .ifEmpty { return 1 }
                .maxBy { it.number.toInt() }
                ?.number!!.toInt() + 1
    }

    fun mapperToNewActivity(activityVO: ActivityVO, account: AccountEntity): ActivityEntity {
        return ActivityEntity(
                account = account,
                type = ActivityType.valueOf(activityVO.type),
                value = activityVO.value
        )
    }

    fun toAccountVO(account: AccountEntity): AccountVO {
        val activities = toListOfActivityVO(account.activities)
        return AccountVO(
                number = account.number,
                amount = account.amount,
                personName = account.person.name,
                activities = activities
        )
    }

    fun toNewAccount(accountVO: AccountVO): AccountEntity {
        val person = PersonEntity(name = accountVO.personName)
        val account = AccountEntity(person = person, number = accountVO.number, amount = accountVO.amount)
        val activity = ActivityEntity(account = account, type = ActivityType.CREDIT, value = accountVO.amount)
        account.activities.add(activity)
        return account
    }

    fun toListOfActivityVO(activityEntities: List<ActivityEntity>): List<ActivityVO> {
        return activityEntities.map { ActivityVO(value = it.value, type = it.type.name) }
    }

}