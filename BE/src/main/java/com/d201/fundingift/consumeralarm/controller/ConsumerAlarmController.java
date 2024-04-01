package com.d201.fundingift.consumeralarm.controller;

import com.d201.fundingift._common.response.ResponseUtils;
import com.d201.fundingift._common.response.SuccessResponse;
import com.d201.fundingift._common.response.SuccessType;
import com.d201.fundingift.consumeralarm.dto.request.PostConsumerAlarmRequest;
import com.d201.fundingift.consumeralarm.dto.response.GetConsumerAlarmResponse;
import com.d201.fundingift.consumeralarm.service.ConsumerAlarmService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Consumer Alarm Controller", description = "사용자 알림 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/consumer-alarms")
public class ConsumerAlarmController {

    private final ConsumerAlarmService consumerAlarmService;

    @Operation(summary = "알람 생성", description = "사용자 알람을 생성합니다.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알람 생성 성공",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")})
    @PostMapping
    public SuccessResponse<Void> createAlarm(@RequestBody PostConsumerAlarmRequest request) {
        consumerAlarmService.createAlarm(request);
        return ResponseUtils.ok(SuccessType.CREATE_ALARM_SUCCESS);
    }

    @Operation(summary = "사용자별 알람 조회", description = "특정 사용자의 모든 알람을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = GetConsumerAlarmResponse.class)))),
            @ApiResponse(responseCode = "404", description = "알람을 찾을 수 없음")})
    @GetMapping("/{consumer-id}")
    public SuccessResponse<List<GetConsumerAlarmResponse>> getAlarmsByConsumerId(@PathVariable("consumer-id") Long consumerId) {
        List<GetConsumerAlarmResponse> response = consumerAlarmService.getAlarmsByConsumerId(consumerId);
        return ResponseUtils.ok(response, SuccessType.GET_ALARM_SUCCESS);
    }

    @Operation(summary = "알람 읽음 상태 업데이트", description = "특정 알람의 읽음 상태를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업데이트 성공"),
            @ApiResponse(responseCode = "404", description = "알람을 찾을 수 없음")})
    @PutMapping("/{alarm-id}")
    public SuccessResponse<Void> updateAlarm(@PathVariable("alarm-id") Long alarmId) {
        consumerAlarmService.updateAlarmReadStatus(alarmId);
        return ResponseUtils.ok(SuccessType.UPDATE_ALARM_SUCCESS);
    }

    @Operation(summary = "알람 삭제", description = "특정 알람을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "알람을 찾을 수 없음")})
    @DeleteMapping("/{alarm-id}")
    public SuccessResponse<Void> deleteAlarm(@PathVariable("alarm-id") Long alarmId) {
        consumerAlarmService.deleteConsumerAlarm(alarmId);
        return ResponseUtils.ok(SuccessType.DELETE_ALARM_SUCCESS);
    }

    @DeleteMapping("/user/{consumer-id}")
    public SuccessResponse<Void> deleteAlarmsByUserId(@PathVariable("consumer-id") Long consumerId) {
        consumerAlarmService.deleteAlarmsByConsumerId(consumerId);
        return ResponseUtils.ok(SuccessType.DELETE_ALARM_SUCCESS);
    }
}