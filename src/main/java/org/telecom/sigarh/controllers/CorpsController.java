package org.telecom.sigarh.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telecom.sigarh.entities.Corps;
import org.telecom.sigarh.services.CorpsService;

import java.util.List;

@RestController
@RequestMapping("/corps")
@RequiredArgsConstructor
public class CorpsController {
    private final CorpsService corpsService;

    @PostMapping("/addCorps")
    public ResponseEntity<Corps> addCorps(@RequestBody Corps corps) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(corpsService.addCorps(corps));

    }

    @PatchMapping("/updateCorps/{id}")
    public ResponseEntity<Corps> updateCorps(
            @PathVariable Long id,
            @RequestBody Corps corps) {

        return ResponseEntity.ok(
                corpsService.updateCorps(id, corps));

    }

    @GetMapping("/getAllCorps")
    public List<Corps> getAllCorps() {
        return corpsService.getAllCorps();
    }
}
