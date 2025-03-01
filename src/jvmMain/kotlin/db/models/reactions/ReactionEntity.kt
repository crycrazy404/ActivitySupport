package db.models.reactions

import db.dto.reactions.ReactionDTO
import db.models.reactions.table.ReactionsTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ReactionEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object: LongEntityClass<ReactionEntity>(ReactionsTable)
    var mark by ReactionsTable.mark
    var slide by ReactionsTable.slideId

    fun toDTO(): ReactionDTO = ReactionDTO(
        mark = mark,
        slideId = slide
    )
}