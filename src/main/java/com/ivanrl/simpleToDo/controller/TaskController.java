package com.ivanrl.simpleToDo.controller;

import com.ivanrl.simpleToDo.Task;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private static final String FRAGMENT_TASKS_TASKLIST = "tasks :: taskList";

    private final TaskRepository taskRepository;

    @GetMapping(headers = "HX-Request")
    public String tasks(Model model) {
        model.addAttribute("tasks", this.taskRepository.findAllByDone(false));

        return "tasks :: #content";
    }

    @PostMapping(value = "/add", headers = "HX-Request")
    public String addTask(NewTask newTask,
                          Model model) {
        this.taskRepository.save(new Task(newTask.name()));

        model.addAttribute("tasks", this.taskRepository.findAllByDone(false));

        return FRAGMENT_TASKS_TASKLIST;
    }

    @PatchMapping(value = "/complete/{id}", headers = "HX-Request")
    public String completeTask(@PathVariable Long id,
                               Model model) {
        this.taskRepository.completeTask(id);

        model.addAttribute("tasks", this.taskRepository.findAllByDone(false));

        return FRAGMENT_TASKS_TASKLIST;
    }
}

interface TaskRepository extends JpaRepository<Task, Long> {
    Collection<Task> findAllByDone(boolean done);

    @Transactional
    @Modifying
    @Query("""
            UPDATE Task SET done = TRUE WHERE ID = :id
            """)
    void completeTask(@NonNull Long id);
}
