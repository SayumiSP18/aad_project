package com.example.aad_project.controller;

import com.example.aad_project.constant.CommonResponse;
import com.example.aad_project.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/analytics")
@RequiredArgsConstructor
@Slf4j
public class AnalyticsController {

    private final DeliveryRepository deliveryRepository;

    @GetMapping("/status-summary")
    @PreAuthorize("hasRole('ADMIN')")   // match whatever syntax your other controllers use
    public ResponseEntity<CommonResponse> statusSummary() {
        log.info("Fetching delivery status summary for analytics dashboard");
        List<Object[]> raw = deliveryRepository.countByStatus();
        Map<String, Long> result = new LinkedHashMap<>();
        for (Object[] row : raw) result.put(String.valueOf(row[0]), (Long) row[1]);
        return ResponseEntity.ok(new CommonResponse(200, result, "Status summary fetched"));
    }

    @GetMapping("/deliveries-per-day")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse> deliveriesPerDay(
            @RequestParam(defaultValue = "14") int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        List<Object[]> raw = deliveryRepository.countPerDaySince(since);
        Map<String, Long> result = new LinkedHashMap<>();
        for (Object[] row : raw) result.put(String.valueOf(row[0]), (Long) row[1]);
        return ResponseEntity.ok(new CommonResponse(200, result, "Status summary fetched"));
    }

    @GetMapping("/top-drivers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse> topDrivers() {
        List<Object[]> raw = deliveryRepository.topDrivers();
        List<Map<String, Object>> result = raw.stream().limit(5).map(row -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("driver", row[0]);
            m.put("completed", row[1]);
            return m;
        }).toList();
        return ResponseEntity.ok(new CommonResponse(200, result, "Status summary fetched"));
    }

    @GetMapping("/branch-load")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse> branchLoad() {
        List<Object[]> raw = deliveryRepository.countByBranch();
        Map<String, Long> result = new LinkedHashMap<>();
        for (Object[] row : raw) result.put(String.valueOf(row[0]), (Long) row[1]);
        return ResponseEntity.ok(new CommonResponse(200, result, "Status summary fetched"));
    }
}
