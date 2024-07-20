package com.ivanrl.simpleToDo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TASKS")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    @Column(name = "DONE")
    private boolean done;

    @Column(name = "USERNAME", nullable = false, updatable = false)
    private String username; // TODO Eventually this should be a custom entity

    public Task(String name, String username) {
        this.name = name;
        this.username = username;
        this.done = false;
    }

}