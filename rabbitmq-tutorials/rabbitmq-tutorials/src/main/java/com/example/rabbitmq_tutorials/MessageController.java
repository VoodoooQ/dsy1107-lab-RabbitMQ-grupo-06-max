package com.example.rabbitmq_tutorials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller para enviar mensajes a RabbitMQ
 *
 * Basado en:
 * https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller.html
 */
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private Sender sender;

    /**
     * Enviar un mensaje vía POST
     *
     * Ejemplo:
     * curl -X POST http://localhost:8080/api/messages \
     * -H "Content-Type: application/json" \
     * -d "{\"message\":\"Hello from REST API!\"}"
     */
    @PostMapping
    public ResponseEntity<String> sendMessage(
            @RequestBody MessageRequest request) {

        try {
            sender.sendMessage(request.getMessage());

            return ResponseEntity.ok(
                    "Mensaje enviado: " + request.getMessage()
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * Enviar un mensaje vía GET
     *
     * Ejemplo:
     * http://localhost:8080/api/messages/send?message=HelloWorld
     */
    @GetMapping("/send")
    public ResponseEntity<String> sendMessageGet(
            @RequestParam(name = "message") String message) {

        try {
            sender.sendMessage(message);

            return ResponseEntity.ok(
                    "Mensaje enviado: " + message
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
    }

    /**
     * DTO para recibir el mensaje en JSON
     */
    public static class MessageRequest {

        private String message;

        public MessageRequest() {
        }

        public MessageRequest(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}