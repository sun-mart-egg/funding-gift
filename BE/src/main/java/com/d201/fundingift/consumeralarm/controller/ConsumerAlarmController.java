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
import jakarta.validation.Valid;
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
    @PostMapping
    public SuccessResponse<Void> createAlarm(@Valid @RequestBody PostConsumerAlarmRequest request) {
        consumerAlarmService.createAlarm(request);
        return ResponseUtils.ok(SuccessType.CREATE_ALARM_SUCCESS);
    }

    @Operation(summary = "사용자별 알람 조회", description = "특정 사용자의 모든 알람을 조회합니다.")
    @GetMapping
    public SuccessResponse<List<GetConsumerAlarmResponse>> getAlarmsByConsumerId() {
        List<GetConsumerAlarmResponse> response = consumerAlarmService.getAlarmsByConsumerId();
        return ResponseUtils.ok(response, SuccessType.GET_ALARM_SUCCESS);
    }

    @Operation(summary = "알람 읽음 상태 업데이트", description = "특정 알람의 읽음 상태를 업데이트합니다.")
    @PutMapping("/{consumer-alarm-id}/read")
    public SuccessResponse<Void> updateAlarm(@PathVariable("consumer-alarm-id") String consumerAlarmId) {
        consumerAlarmService.updateAlarmReadStatus(consumerAlarmId);
        return ResponseUtils.ok(SuccessType.UPDATE_ALARM_SUCCESS);
    }

    @Operation(summary = "알람 삭제", description = "특정 알람을 삭제합니다.")
    @DeleteMapping("/{consumer-alarm-id}")
    public SuccessResponse<Void> deleteAlarm(@PathVariable("consumer-alarm-id") String consumerAlarmId) {
        consumerAlarmService.deleteConsumerAlarm(consumerAlarmId);
        return ResponseUtils.ok(SuccessType.DELETE_ALARM_SUCCESS);
    }
    @Operation(summary = "사용자별 알람 삭제", description = "특정 사용자의 모든 알람을 삭제합니다.")
    @DeleteMapping
    public SuccessResponse<Void> deleteAlarmsByUserId() {
        consumerAlarmService.deleteAlarmsByConsumerId();
        return ResponseUtils.ok(SuccessType.DELETE_ALARM_SUCCESS);
    }
}