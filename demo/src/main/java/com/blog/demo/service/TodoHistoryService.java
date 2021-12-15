package com.blog.demo.service;

import com.blog.demo.model.CheckTodoCreation;
import com.blog.demo.model.TodoHistoryEntity;
import com.blog.demo.persistence.CheckTodoCreationRepository;
import com.blog.demo.persistence.TodoHistoryRepository;
import com.blog.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TodoHistoryService {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TodoHistoryRepository todoHistoryRepository;

    @Autowired
    CheckTodoCreationRepository checkTodoCreationRepository;

    public List<TodoHistoryEntity> retrieve(final LocalDate date, final String userId) {
        return todoHistoryRepository.findByTodoDateAndUserIdOrderByRegisteredDateTimeAsc(date, userId);
    }

    /**
     * todoHistory 당일자 생성 시
     * */
    @Scheduled(cron = "3 0 0 * * *")
    @Transactional
    public void scheduledCreate() {
        LocalDate now = LocalDate.now();
        List<TodoHistoryEntity> items = todoRepository.findByDayOfWeekAndUseYn(now.getDayOfWeek().getValue(), "Y")
                .stream().map(todo ->
                    TodoHistoryEntity.builder()
                        .todoDate(now)
                        .title(todo.getTitle())
                        .userId(todo.getUserId())
                        .parentTodoId(todo.getId())
                        .done(false)
                        .build()
                ).collect(Collectors.toList());
        checkTodoCreationRepository.save(CheckTodoCreation.builder()
                .creationDate(now)
                .build());
        todoHistoryRepository.saveAll(items);
    }

    /**
     * todoHistory 당일자 생성 시
     * @param item TodoHistory
     * @return 당일의 TodoHistoryEntity 리스트
     * */
    public List<TodoHistoryEntity> create(TodoHistoryEntity item) {
        TodoHistoryEntity entity = todoHistoryRepository.save(item);
        return todoHistoryRepository.findByTodoDateAndUserIdOrderByRegisteredDateTimeAsc(
                entity.getTodoDate(), entity.getUserId()
        );
    }

    /**
     * todoHistory 업데이트 시
     * @param entity TodoHistory
     * @return TodoHistoryEntity 리스트
     * */
    public List<TodoHistoryEntity> update(final TodoHistoryEntity entity) {
        validate(entity);

        final Optional<TodoHistoryEntity> original = todoHistoryRepository.findById(entity.getId());
        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            if(entity.isDone()) todo.setDoneTime(LocalDateTime.now());
            todoHistoryRepository.save(todo);
        });

        return retrieve(LocalDate.now(), entity.getUserId());
    }

    /**
     * todoHistory 삭제 시
     * @param entity TodoHistory
     * @return TodoHistoryEntity 리스트
     * */
    public List<TodoHistoryEntity> delete(final TodoHistoryEntity entity) {

        todoHistoryRepository.delete(entity);

        return retrieve(LocalDate.now(), entity.getUserId());
    }

    private void validate(final TodoHistoryEntity entity) {
        // Validations
        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
}
