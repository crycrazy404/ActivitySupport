package db.models.reactions.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object ReactionsTable: LongIdTable() {
    val mark: Column<Int> = integer("mark")
    val slideId: Column<Int> = integer("slideId")
}