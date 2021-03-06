package com.todo.daily.controller;

import com.todo.daily.dto.ResponseDTO;
import com.todo.daily.model.TodoHistoryEntity;
import com.todo.daily.service.TodoHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/todoHistory")
public class TodoHistoryController {

    @Autowired
    TodoHistoryService todoHistoryService;

    @GetMapping("/{date}")
    public ResponseEntity<?> retrieveTodoHistory(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @AuthenticationPrincipal String userId) {
        List<TodoHistoryEntity> list = todoHistoryService.retrieve(date, userId);
        return ResponseEntity.ok(ResponseDTO.<TodoHistoryEntity>builder().data(list).build());
    }

    @PostMapping
    public ResponseEntity<?> createTodoHistory(@RequestBody TodoHistoryEntity entity,
                                               @AuthenticationPrincipal String userId) {
        try {
            entity.setId(null);
            entity.setUserId(userId);
            entity.setTodoDate(LocalDate.now());
            List<TodoHistoryEntity> list = todoHistoryService.create(entity);
            return ResponseEntity.ok(ResponseDTO.<TodoHistoryEntity>builder().data(list).build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder().error(e.getMessage()).build());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTodoHistory(@RequestBody TodoHistoryEntity entity) {
        try {
            List<TodoHistoryEntity> list = todoHistoryService.update(entity);
            return ResponseEntity.ok(ResponseDTO.<TodoHistoryEntity>builder().data(list).build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder().error(e.getMessage()).build());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodoHistory(@RequestBody TodoHistoryEntity entity) {
        try {
            List<TodoHistoryEntity> list = todoHistoryService.delete(entity);
            return ResponseEntity.ok(ResponseDTO.<TodoHistoryEntity>builder().data(list).build());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder().error(e.getMessage()).build());
        }
    }
}
