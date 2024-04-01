package com.d201.fundingift.attendance.controller;

import com.d201.fundingift._common.response.*;
import com.d201.fundingift.attendance.dto.request.PostAttendanceRequest;
import com.d201.fundingift.attendance.dto.response.GetAttendanceDetailResponse;
import com.d201.fundingift.attendance.dto.response.GetAttendancesResponse;
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
    public SuccessResponse<Void> postFunding(@RequestBody @Valid PostAttendanceRequest postAttendanceRequest) {

        attendanceService.postAttendance(postAttendanceRequest);
        return ResponseUtils.ok(POST_ATTENDANCE_SUCCESS);
    }

    @Operation(summary = "내 펀딩의 참여자 상세 정보 리스트",
            description = """
                           `token` \n
                           내 펀딩의 참여자 상세 정보 리스트를 보여줍니다.\n
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

        return ResponseUtils.ok(attendanceService.getAttendancesResponse(fundingId, pageable), SuccessType.GET_ATTENDANCE_DETAIL_SUCCESS);
    }

}
