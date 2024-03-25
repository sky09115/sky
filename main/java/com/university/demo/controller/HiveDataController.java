package com.university.demo.controller;
import com.university.demo.service.HiveQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
public class HiveDataController {

    private final HiveQueryService hiveQueryService;

    @Autowired
    public HiveDataController(HiveQueryService hiveQueryService) {
        this.hiveQueryService = hiveQueryService;
    }

    @GetMapping("/query")
    public ResponseEntity<List<Map<String, Object>>> queryHive(@RequestParam String sql) {
        List<Map<String, Object>> result = hiveQueryService.executeQuery(sql);
        return ResponseEntity.ok(result);
    }
}
