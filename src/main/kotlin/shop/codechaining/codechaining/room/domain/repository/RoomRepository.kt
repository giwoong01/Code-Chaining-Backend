package shop.codechaining.codechaining.room.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import shop.codechaining.codechaining.member.domain.Member
import shop.codechaining.codechaining.room.domain.Room

interface RoomRepository : JpaRepository<Room, Long> {
    fun findAllByMember(member: Member): List<Room>
}