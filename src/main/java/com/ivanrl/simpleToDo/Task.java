package com.ivanrl.simpleToDo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TASKS")
@Getter
@Setter
@NoArgsConstructor
public class Task {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "DONE")
    private boolean done;

    public Task(String name) {
        this.name = name;
        this.done = false;
    }

}