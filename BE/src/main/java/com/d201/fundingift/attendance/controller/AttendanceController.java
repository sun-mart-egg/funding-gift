package com.d201.fundingift.attendance.controller;

import com.d201.fundingift._common.response.*;
import com.d201.fundingift.attendance.dto.request.PostAttendanceRequest;
import com.d201.fundingift.attendance.dto.request.UpdateAttendanceRequest;
import com.d201.fundingift.attendance.dto.response.GetAttendanceDetailResponse;
import com.d201.fundingift.attendance.dto.response.GetAttendancesResponse;
import com.d201.fundingift.attendance.dto.response.PostAttendanceResponse;
import com.d201.fundingift.attendance.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static com.d201.fundingift._common.response.SuccessType.POST_ATTENDANCE_SUCCESS;

@Tag(name = "attendance", description = "펀딩 참여 관련 API")
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Operation(summary = "펀딩 참여 생성",
            description = """
                           `token` \n
                           펀딩에 참여합니다. \n
                           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400",
                    description = "펀딩이 없는 경우 / 펀딩 상태가 IN_PROGRESS가 아닌 경우 / 펀딩 참여자의 친구 목록에 펀딩 생성자가 없는 경우 / 최소 금액 만족 안하는 경우 / 목표 금액을 넘는 경우",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @PostMapping
    public SuccessResponse<PostAttendanceResponse> postFunding(@RequestBody @Valid PostAttendanceRequest postAttendanceRequest) {

        return ResponseUtils.ok(attendanceService.postAttendance(postAttendanceRequest), POST_ATTENDANCE_SUCCESS);
    }

    @Operation(summary = "내 펀딩의 참여자 리스트",
            description = """
                           `token` \n
                           내 펀딩의 참여자 리스트를 보여줍니다.\n
                           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400",
                    description = "로그인 여부 / 펀딩 존재 여부 / 내 펀딩이 맞는지 여부",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @GetMapping("/list")
    public SuccessResponse<SliceList<GetAttendancesResponse>> getAttendancesResponse(@RequestParam(required = true, name = "funding-id") Long fundingId,
                                                                                     @PageableDefault(size=4, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseUtils.ok(attendanceService.getAttendancesResponse(fundingId, pageable), SuccessType.GET_ATTENDANCE_SUCCESS);
    }

    @Operation(summary = "펀딩참여 정보 상세 조회",
            description = """
                           `token` \n
                           펀딩참여 정보 상세 조회를 합니다.\n
                           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400",
                    description = "로그인 여부 / 펀딩 존재 여부 / 펀딩 참여 존재 여부",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "401",
                    description = "펀딩 참여 상세 정보 조회 권한 확인 - 펀딩 참여자나 펀딩 생성자만 상세 조회 가능",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @GetMapping("/detail")
    public SuccessResponse<GetAttendanceDetailResponse> getAttendanceDetailResponse(@RequestParam(required = true, name = "attendance-id") Long attendanceId,
                                                                                    @RequestParam(required = true, name="funding-id") Long fundingId) {

        return ResponseUtils.ok(attendanceService.getAttendanceDetailResponse(attendanceId, fundingId), SuccessType.GET_ATTENDANCE_DETAIL_SUCCESS);
    }

    @Operation(summary = "펀딩참여자에게 감사 메시지 작성",
            description = """
                           `token` \n
                           펀딩참여자에게 감사 메시지를 작성 합니다.\n
                           """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "성공",
                    useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400",
                    description = "로그인 여부 / 펀딩 존재 여부 / 펀딩 참여 존재 여부",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "401",
                    description = "감사 메시지 작성 권한 여부 - 펀딩 생성자만 작성 가능",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @PutMapping("/write-message")
    public SuccessResponse<Void> updateReceiveMessage(@RequestBody UpdateAttendanceRequest updateAttendanceRequest) {
        attendanceService.updateReceiveMessage(updateAttendanceRequest);

        return ResponseUtils.ok(SuccessType.UPDATE_ATTENDANCE_RECEIVE_MESSAGE_SUCCESS);
    }
}
