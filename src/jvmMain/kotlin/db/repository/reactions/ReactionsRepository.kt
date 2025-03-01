package db.repository.reactions

import db.dto.reactions.ReactionDTO
import db.models.reactions.ReactionEntity
import db.models.reactions.table.ReactionsTable
import org.jetbrains.exposed.sql.transactions.transaction

class ReactionsRepository: IReactionsRepository {
    override fun save(dto: ReactionDTO): ReactionEntity {
        return transaction {
            ReactionEntity.new {
                mark = dto.mark
                slide = dto.slideId
            }
        }
    }

    override fun getAll(): List<ReactionDTO> {
       return transaction{
           ReactionEntity.all().toList().map { it.toDTO() }
       }
    }

    override fun getBySlideID(id: Int): List<ReactionDTO> {
        return transaction {
            ReactionEntity.find { ReactionsTable.slideId eq id }.map { it.toDTO() }
        }
    }


}