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
public class RootController {

    private static final String FRAGMENT_TASKS_TASKLIST = "tasks :: taskList";
    private static final NavBarLink[] navigationLinks = {
            new NavBarLink("Home", "/home"),
            new NavBarLink("Tasks", "/tasks")
    };

    private final TaskRepository taskRepository;

    @GetMapping(value = "")
    public String index(Model model) {
        model.addAttribute("tasks", this.taskRepository.findAllByDone(false));
        model.addAttribute("navLinks", navigationLinks);

        return "index";
    }

    @GetMapping(value = "home", headers = "HX-Request")
    public String home(Model model) {
        return "home";
    }

    @GetMapping(value = "tasks", headers = "HX-Request")
    public String tasks(Model model) {
        model.addAttribute("tasks", this.taskRepository.findAllByDone(false));

        return "tasks :: #content";
    }

    @PostMapping(value = "add", headers = "HX-Request")
    public String addTask(NewTask newTask,
                          Model model) {
        this.taskRepository.save(new Task(newTask.name()));

        model.addAttribute("tasks", this.taskRepository.findAllByDone(false));

        return FRAGMENT_TASKS_TASKLIST;
    }

    @DeleteMapping(value = "complete/{id}", headers = "HX-Request")
    public String completeTask(@PathVariable Long id,
                             Model model) {
        this.taskRepository.deleteById(id);

        model.addAttribute("tasks", this.taskRepository.findAllByDone(false));

        return FRAGMENT_TASKS_TASKLIST;
    }
}

interface TaskRepository extends JpaRepository<Task, Long> {

    Collection<Task> findAllByDone(boolean done);
}

record NewTask(String name) {}
record NavBarLink(String text, String link) {}

