package db.repository.reactions

import db.dto.reactions.ReactionDTO
import db.models.reactions.ReactionEntity

interface IReactionsRepository {

    fun save(dto: ReactionDTO): ReactionEntity

    fun getAll(): List<ReactionDTO>

    fun getBySlideID(id: Int): List<ReactionDTO>
}