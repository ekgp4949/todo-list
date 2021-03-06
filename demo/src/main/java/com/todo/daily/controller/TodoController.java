package com.todo.daily.controller;

import com.todo.daily.dto.ResponseDTO;
import com.todo.daily.dto.TodoDTO;
import com.todo.daily.model.TodoEntity;
import com.todo.daily.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@PostMapping
	public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
		try {
			TodoEntity entity = TodoDTO.toEntity(dto);
			// id 초기화
			entity.setId(null);
			
			entity.setUserId(userId);
			entity.setUseYn("Y");
			
			List<TodoEntity> entities = todoService.create(entity);
			
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			return ResponseEntity.ok(responseDTO);
		} catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	@GetMapping("/{dayOfWeek}")
	public ResponseEntity<?> retrieveTodoList(@PathVariable int dayOfWeek, @AuthenticationPrincipal String userId) {
		try {
			List<TodoEntity> entities = todoService.retrieve(dayOfWeek, userId);
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			return ResponseEntity.ok(ResponseDTO.<TodoDTO>builder().data(dtos).build());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
		List<TodoEntity> entities = todoService.retrieve(userId);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
		TodoEntity entity = TodoDTO.toEntity(dto);
		entity.setUserId(userId);
		
		List<TodoEntity> entities = todoService.update(entity);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
		try {
			TodoEntity entity = TodoDTO.toEntity(dto);
			entity.setUserId(userId);
			
			List<TodoEntity> entities = todoService.delete(entity, userId);
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			return ResponseEntity.ok().body(responseDTO);
		} catch(Exception e) {
			ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
}
