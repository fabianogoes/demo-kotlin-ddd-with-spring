package com.example.demo.boundaries.account.infrastructure

import com.example.demo.boundaries.account.domain.AccountVO
import com.example.demo.boundaries.account.domain.MovementVO
import org.springframework.stereotype.Repository

@Repository
internal class AccountRepositoryHibernateAdapter(val repositoryJpa: AccountRepositoryJpa) : AccountRepository {

    override fun persisNewAccount(accountVo: AccountVO) {
        val account: Account? = this.toNewAccount(accountVo)
        account?.let { repositoryJpa.save(it) }
    }

    override fun retrieveAllMovements(number: String): List<MovementVO> {
        return repositoryJpa.findByNumber(number)
                .orElseThrow { throw AccountNotFoundException("Account Not Found") }
                .movements.map { movement -> MovementVO(value = movement.value, type = movement.type.name) }
    }

    override fun retrieveAccountByNumber(accountNumber: String): AccountVO {
        val account = repositoryJpa.findByNumber(accountNumber)
                .orElseThrow { throw AccountNotFoundException("Account Not Found") }
        return this.toAccountVO(account)
    }

    override fun persistNewMovementAndUpdateAmount(accountVO: AccountVO, movementVO: MovementVO) {
        val accountEntity = repositoryJpa.findByNumber(accountVO.number)
                .orElseThrow{ throw AccountNotFoundException("Account Not Found") }
        accountEntity.amount = accountVO.amount
        val newMovement: Movement = mapperToNewMovement(movementVO, accountEntity)
        accountEntity.movements.add(newMovement)
        repositoryJpa.save(accountEntity)
    }

    override fun retrieveAllAccount(): List<AccountVO> {
        return repositoryJpa.findAll().map { this.toAccountVO(it) }
    }

    override fun generateNextAccountNumber(): Int {
        return repositoryJpa.findAll()
                .ifEmpty { return 1 }
                .maxBy { it.number.toInt() }
                ?.number!!.toInt() + 1
    }

    fun mapperToNewMovement(movementVO: MovementVO, account: Account): Movement {
        return Movement(
                account = account,
                type = MovementType.valueOf(movementVO.type),
                value = movementVO.value
        )
    }

    fun toAccountVO(account: Account): AccountVO {
        val movements = toListOfMovementVO(account.movements)
        return AccountVO(
                number = account.number,
                amount = account.amount,
                personName = account.person.name,
                movements = movements
        )
    }

    fun toNewAccount(accountVO: AccountVO): Account {
        val person = Person(name = accountVO.personName)
        val account = Account(person = person, number = accountVO.number, amount = accountVO.amount)
        val movement = Movement(account = account, type = MovementType.CREDIT, value = accountVO.amount)
        account.movements.add(movement)
        return account
    }

    fun toListOfMovementVO(movementEntities: List<Movement>): List<MovementVO> {
        return movementEntities.map { MovementVO(value = it.value, type = it.type.name) }
    }

}