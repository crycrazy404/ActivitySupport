package repository

//class AttendanceRepository: CrudRepository<AttendanceEntity, UUID> {
//    override fun create(entity: AttendanceEntity): UUID {
//        return transaction {
//            AttendanceTable.insertAndGetId {
//                it[userId] = entity.userId
//                it[date] = entity.date
//                it[status] = entity.status
//            }.value
//        }
//    }
//
//    override fun findById(id: UUID): AttendanceEntity? {
//        return transaction {
//            AttendanceTable.selectAll().where { AttendanceTable.id eq id }
//                .map { it.toAttendance() }
//                .singleOrNull()
//        }
//    }
//
//    override fun findAll(): List<AttendanceEntity> {
//        return transaction {
//            AttendanceTable.selectAll().map { it.toAttendance() }
//        }    }
//
//    override fun deleteById(id: UUID): Boolean {
//        return transaction {
//            AttendanceTable.deleteWhere { AttendanceTable.id eq id } > 0
//        }
//    }
//
//    override fun update(entity: AttendanceEntity): Boolean {
//        return transaction {
//            AttendanceTable.update({ AttendanceTable.id eq entity.id }) {
//                it[userId] = entity.userId
//                it[date] = entity.date
//                it[status] = entity.status
//            } > 0
//        }
//    }
//}