package com.example.demo.boundaries.account.infrastructure.repository

import com.example.demo.boundaries.account.crosscutting.MovementType
import com.example.demo.boundaries.account.domain.exception.AccountNotFoundException
import com.example.demo.boundaries.account.domain.persistence.AccountPersistencePort
import com.example.demo.boundaries.account.domain.vo.AccountVO
import com.example.demo.boundaries.account.domain.vo.MovementVO
import com.example.demo.boundaries.account.infrastructure.entity.AccountEntity
import com.example.demo.boundaries.account.infrastructure.entity.MovementEntity
import com.example.demo.boundaries.account.infrastructure.entity.PersonEntity
import org.springframework.stereotype.Repository

@Repository
internal class AccountPersistenceAdapter(val accountRepository: AccountRepository) : AccountPersistencePort {

    override fun persisNewAccount(accountVo: AccountVO) {
        val account: AccountEntity? = this.toNewAccount(accountVo)
        account?.let { accountRepository.save(it) }
    }

    override fun retrieveAllMovements(number: String): List<MovementVO> {
        return accountRepository.findByNumber(number)
                .orElseThrow { throw AccountNotFoundException("Account Not Found") }
                .movements.map { movement -> MovementVO(value = movement.value, type = movement.type.name) }
    }

    override fun retrieveAccountByNumber(accountNumber: String): AccountVO {
        val account = accountRepository.findByNumber(accountNumber)
                .orElseThrow { throw AccountNotFoundException("Account Not Found") }
        return this.toAccountVO(account)
    }

    override fun persistNewMovementAndUpdateAmount(accountVO: AccountVO, movementVO: MovementVO) {
        val accountEntity = accountRepository.findByNumber(accountVO.number)
                .orElseThrow{ throw AccountNotFoundException("Account Not Found") }
        accountEntity.amount = accountVO.amount
        val newMovement: MovementEntity = mapperToNewMovement(movementVO, accountEntity)
        accountEntity.movements.add(newMovement)
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

    fun mapperToNewMovement(movementVO: MovementVO, account: AccountEntity): MovementEntity {
        return MovementEntity(
                account = account,
                type = MovementType.valueOf(movementVO.type),
                value = movementVO.value
        )
    }

    fun toAccountVO(account: AccountEntity): AccountVO {
        val movements = toListOfMovementVO(account.movements)
        return AccountVO(
                number = account.number,
                amount = account.amount,
                personName = account.person.name,
                movements = movements
        )
    }

    fun toNewAccount(accountVO: AccountVO): AccountEntity {
        val person = PersonEntity(name = accountVO.personName)
        val account = AccountEntity(person = person, number = accountVO.number, amount = accountVO.amount)
        val movement = MovementEntity(account = account, type = MovementType.CREDIT, value = accountVO.amount)
        account.movements.add(movement)
        return account
    }

    fun toListOfMovementVO(movementEntities: List<MovementEntity>): List<MovementVO> {
        return movementEntities.map { MovementVO(value = it.value, type = it.type.name) }
    }

}