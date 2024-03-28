package com.d201.fundingift.attendance.controller;

import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift.attendance.dto.request.PostAttendanceRequest;
import com.d201.fundingift.attendance.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.d201.fundingift._common.response.SuccessType.POST_ATTENDANCE_SUCCESS;

@RestController
@RequestMapping("api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public SuccessResponse<Void> postFunding(@RequestBody @Valid PostAttendanceRequest postAttendanceRequest) {

        attendanceService.postAttendance(postAttendanceRequest);
        return ResponseUtils.ok(POST_ATTENDANCE_SUCCESS);
    }
}
