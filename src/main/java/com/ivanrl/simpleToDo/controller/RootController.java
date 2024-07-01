package com.ivanrl.simpleToDo.controller;

import com.ivanrl.simpleToDo.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
@Slf4j
public class RootController {

    private static final NavBarLink[] navigationLinks = {
            new NavBarLink("Home", "/home"),
            new NavBarLink("Tasks", "/tasks")
    };

    private final TaskRepository taskRepository;

    @GetMapping(value = "")
    public String index(Model model) {
        model.addAttribute("tasks", this.taskRepository.findAllByDone(false));
        model.addAttribute("navLinks", navigationLinks);
        model.addAttribute("isHome", true);

        return "index";
    }

    @GetMapping(value = "home", headers = "HX-Request")
    public String home(Model model) {
        return "home";
    }

    // This should only return a fragment for main element with the tasks
    @GetMapping(value = "tasks", headers = "HX-Request")
    public String tasks(Model model) {
        model.addAttribute("tasks", this.taskRepository.findAllByDone(false));

        return "tasks :: #content";
    }

    @PostMapping(value = "add", headers = "HX-Request")
    public String addTask(NewTask newTask,
                          Model model) {
        log.debug("Creating new task: {}", newTask.name());

        this.taskRepository.save(new Task(newTask.name()));

        model.addAttribute("tasks", this.taskRepository.findAllByDone(false));
        return "tasks :: taskList";
    }

    @DeleteMapping(value = "complete/{id}", headers = "HX-Request")
    public String completeTask(@PathVariable Long id,
                             Model model) {
        log.debug("Completing task {}", id);

        this.taskRepository.deleteById(id);
        model.addAttribute("tasks", this.taskRepository.findAllByDone(false));
        return "index::taskList";
    }
}

interface TaskRepository extends JpaRepository<Task, Long> {

    Collection<Task> findAllByDone(boolean done);
}

record NewTask(String name) {}
record NavBarLink(String text, String link) {}

