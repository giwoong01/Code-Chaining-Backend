package shop.codechaining.codechaining.room.api

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import shop.codechaining.codechaining.global.template.RspTemplate
import shop.codechaining.codechaining.room.api.request.RoomSaveReqDto
import shop.codechaining.codechaining.room.api.request.RoomUpdateReqDto
import shop.codechaining.codechaining.room.api.request.ScrapReqDto
import shop.codechaining.codechaining.room.api.response.MyRoomsResDto
import shop.codechaining.codechaining.room.api.response.PublicRoomsResDto
import shop.codechaining.codechaining.room.api.response.RoomInfoResDto
import shop.codechaining.codechaining.room.application.RoomService

@RestController
@RequestMapping("/api/room")
class RoomController(
    private val roomService: RoomService
) {
    @PostMapping("/")
    fun roomSave(
        @AuthenticationPrincipal email: String,
        @RequestBody @Valid roomSaveReqDto: RoomSaveReqDto
    ): RspTemplate<String> {
        roomService.roomSave(email, roomSaveReqDto)
        return RspTemplate(HttpStatus.CREATED, "토론 방 생성")
    }

    @PutMapping("/{roomId}")
    fun roomUpdate(
        @AuthenticationPrincipal email: String,
        @PathVariable roomId: Long,
        @RequestBody @Valid roomUpdateReqDto: RoomUpdateReqDto
    ): RspTemplate<String> {
        roomService.roomUpdate(email, roomId, roomUpdateReqDto)
        return RspTemplate(HttpStatus.OK, "토론 방 수정")
    }

    @GetMapping("/{roomId}")
    fun roomInfo(@AuthenticationPrincipal email: String, @PathVariable roomId: Long): RspTemplate<RoomInfoResDto> {
        val roomInfo = roomService.roomInfo(email, roomId)
        return RspTemplate(HttpStatus.OK, "토론 방 정보", roomInfo)
    }

    @GetMapping("/my")
    fun myRooms(@AuthenticationPrincipal email: String): RspTemplate<MyRoomsResDto> {
        val myRooms = roomService.myRooms(email)
        return RspTemplate(HttpStatus.OK, "내 토론 방", myRooms)
    }

    @GetMapping("/my/scrap")
    fun myScrapRooms(@AuthenticationPrincipal email: String): RspTemplate<PublicRoomsResDto> {
        val myScrapRooms = roomService.myScrapRooms(email)
        return RspTemplate(HttpStatus.OK, "내가 스크랩한 토론 방", myScrapRooms)
    }

    @GetMapping("/public/search")
    fun publicRooms(
        @AuthenticationPrincipal email: String,
        @RequestParam filter: String
    ): RspTemplate<PublicRoomsResDto> {
        val publicRooms = roomService.publicRooms(email, filter)
        return RspTemplate(HttpStatus.OK, "공개 토론 방", publicRooms)
    }

    @DeleteMapping("/{roomId}")
    fun roomDelete(@AuthenticationPrincipal email: String, @PathVariable roomId: Long): RspTemplate<String> {
        roomService.deleteMyRoom(email, roomId)
        return RspTemplate(HttpStatus.OK, "토론 방 삭제")
    }

    @PostMapping("/scrap")
    fun roomScrap(@AuthenticationPrincipal email: String, @RequestBody scrapReqDto: ScrapReqDto): RspTemplate<String> {
        roomService.roomScrap(email, scrapReqDto)
        return RspTemplate(HttpStatus.OK, "토론 방 스크랩")
    }

    @PostMapping("/scrap/delete")
    fun roomScrapDelete(
        @AuthenticationPrincipal email: String,
        @RequestBody scrapReqDto: ScrapReqDto
    ): RspTemplate<String> {
        roomService.roomScrapDelete(email, scrapReqDto)
        return RspTemplate(HttpStatus.OK, "토론 방 스크랩 삭제")
    }

}