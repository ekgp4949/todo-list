package com.todo.daily.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="check_todo_creation")
public class CheckTodoCreation {

    @Id
    private LocalDate todoDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registeredDateTime;

}
