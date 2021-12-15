package com.blog.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="CHECK_TODO_CREATION")
public class CheckTodoCreation {

    @Id
    private LocalDate creationDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime registeredDateTime;

}
