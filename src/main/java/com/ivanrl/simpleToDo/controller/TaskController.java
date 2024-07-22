package com.ivanrl.simpleToDo.controller;

import com.ivanrl.simpleToDo.Task;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collection;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;

    @GetMapping(headers = "HX-Request")
    public String tasks(@AuthenticationPrincipal UserDetails user,
                        Model model) {

        model.addAttribute("tasks", this.taskRepository.findAllByDoneAndUsername(false, user.getUsername()));

        return "tasks";
    }

    @Transactional
    @PostMapping(value = "/add", headers = "HX-Request")
    public String addTask(NewTask newTask,
                          @AuthenticationPrincipal UserDetails user,
                          Model model) {
        var savedEntity = this.taskRepository.save(new Task(newTask.name(), user.getUsername()));

        model.addAttribute("task", savedEntity);

        return "task :: newTask";
    }

    @Transactional
    @PatchMapping(value = "/complete/{id}", headers = "HX-Request")
    public ResponseEntity<Void> completeTask(@PathVariable Long id) {
        var affectedRows = this.taskRepository.completeTask(id);
        if (affectedRows != 1) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, "Task not found");
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}", headers = "HX-Request")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        this.taskRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}

interface TaskRepository extends JpaRepository<Task, Long> {
    Collection<Task> findAllByDoneAndUsername(boolean done, String username);


    @Modifying
    @Query("""
            UPDATE Task SET done = TRUE WHERE ID = :id
            """)
    int completeTask(@NonNull Long id);
}
