package br.com.comprebem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SaudeController {
    @GetMapping("/health")
    public Map<String, String> verificarSaude() {
        return Map.of("status", "UP");
    }
}
