package com.chatgpt.controller;

import com.chatgpt.dto.UserRequestBodyDto;
import com.chatgpt.models.Conversation;
import com.chatgpt.service.ChatGptService;
import com.chatgpt.util.JVMInfoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatGPTController {

    final private ChatGptService service;

    @PostMapping("/prompts")
    public ResponseEntity<String> chat(@RequestBody UserRequestBodyDto request) {

        String response = service.chat(
                request.getToken(), request.getPrompt(), request.getName(), request.getConversationId()
        );
        if(response != null) {
            return ResponseEntity.ok(response);
        }
        return new ResponseEntity<>(
                null,
                HttpStatus.BAD_REQUEST
        );
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<Conversation>> conversations(@RequestBody UserRequestBodyDto request) {
        List<Conversation> conversations = service.conversations(request.getToken());
        if (conversations != null) {
            return ResponseEntity.ok(conversations);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

























    @GetMapping("/jvm/info")
    public Map<String, Object> getJVMInfo() {
        Map<String, Object> jvmInfo = new HashMap<>();

        // Ajouter les informations mémoire
        jvmInfo.put("memory", JVMInfoUtil.getMemoryInfo());

        // Ajouter les informations des threads
        jvmInfo.put("threads", JVMInfoUtil.getThreadInfo());

        // Ajouter les informations système
        jvmInfo.put("system", JVMInfoUtil.getSystemInfo());

        return jvmInfo;
    }

    @GetMapping("/jvm/garbageCollector")
    public ResponseEntity<String>  getClean() {
        System.out.println("Mémoire avant création d'objets:");
        printMemoryStatus();

        // Créer des objets pour remplir la heap
        createObjects();

        // Afficher l'état après avoir créé les objets
        System.out.println("Mémoire après création d'objets:");
        printMemoryStatus();

        // Forcer un Garbage Collection
        System.gc();

        // Afficher l'état après la collecte des ordures
        System.out.println("Mémoire après Garbage Collection:");
        printMemoryStatus();
        return ResponseEntity.ok("done");
    }



    private void printMemoryStatus() {
        // Utiliser Runtime pour obtenir des informations sur la mémoire
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        System.out.println("Mémoire totale : " + formatBytes(totalMemory));
        System.out.println("Mémoire libre : " + formatBytes(freeMemory));
        System.out.println("Mémoire utilisée : " + formatBytes(usedMemory));
    }

    private void createObjects() {
        for (int i = 0; i < 10000; i++) {
            // Allouer un objet dans la heap
            String str = new String("Object " + i);
        }
    }






    private String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " bytes";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024 * 1024));
        }
    }



}
