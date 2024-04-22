package by.bsuir.constructioncompany.controllers;

import by.bsuir.constructioncompany.requests.TaskRequest;
import by.bsuir.constructioncompany.requests.TaskStatusChangeRequest;
import by.bsuir.constructioncompany.responses.TaskResponse;
import by.bsuir.constructioncompany.services.AuthenticationService;
import by.bsuir.constructioncompany.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final AuthenticationService authenticationService;

    public TaskController(TaskService taskService, AuthenticationService authenticationService) {
        this.taskService = taskService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/project/{id}")
    @PreAuthorize("@projectSecurity.hasForemanAccess(#id, #principal)")
    public ResponseEntity<String> createTask(@PathVariable("id") Long id, @RequestBody @Valid TaskRequest taskRequest, Principal principal){
        taskService.createTask(taskRequest, id);
        return ResponseEntity.ok("Задание создано");
    }

    @DeleteMapping("{taskId}/project/{id}")
    @PreAuthorize("@projectSecurity.hasForemanAccess(#id, #principal)")
    public ResponseEntity<String> createTask(@PathVariable("id") Long id, @PathVariable("taskId") Long taskId, Principal principal){
        taskService.deleteTask(id, taskId);
        return ResponseEntity.ok("Задание удалено");
    }

    @GetMapping("/builder")
    public ResponseEntity<List<TaskResponse>> getTasks(Principal principal){
        return ResponseEntity.ok(taskService.getTasksBuilder(authenticationService.getUserByPrincipal(principal)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@projectSecurity.hasBuilderAccess(#id, #principal)")
    public ResponseEntity<String> changeStatusTask(Principal principal, @PathVariable("id") long id, @Valid @RequestBody TaskStatusChangeRequest taskStatusChangeRequest){
        taskService.changeTaskStatus(taskStatusChangeRequest,id);
        return ResponseEntity.ok("Статус изменен");
    }
}
