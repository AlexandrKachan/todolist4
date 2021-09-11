package by.lex.todolist.controller;

import by.lex.todolist.security.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import by.lex.todolist.controller.except.ResourceNotFoundException;
import by.lex.todolist.model.TodoModel;
import by.lex.todolist.service.TodoService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TodoController {

    private final TodoService toDoService;

    @Autowired
    public TodoController(TodoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("")
    public String mainPage() {
        return "redirect:/todo/all";
    }

    @GetMapping("/todo/all")
    public String allTodosPage(Model model) {
        List<TodoModel> todos = Utils.getCurrentUser()
                .map(toDoService::findToDoByUsername)
                .orElseThrow(IllegalStateException::new);
        model.addAttribute("todos", todos);

        Set<String> userTodoListsNames = todos.stream()
                .map(TodoModel::getTodoListName)
                .collect(Collectors.toSet());
        model.addAttribute("userTodoListsNames", userTodoListsNames);

        return "todoList";
    }

    @GetMapping("/todo/{id}")
    public String todoPage(@PathVariable("id") Long id, Model model) {
        TodoModel toDoModel = toDoService.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        model.addAttribute("todo", toDoModel);
        return "todo";
    }

    @GetMapping("/todo/create")
    public String createTodoPage(Model model) {
        model.addAttribute("todo", new TodoModel());
        return "todo";
    }

    @PostMapping("/todo/create")
    public String createTodoPost(@ModelAttribute("todo") @Valid TodoModel toDoModel,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }
        toDoService.create(toDoModel);
        return "redirect:/";
    }

    @GetMapping("/todo/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        toDoService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/todo/complete/{id}")
    public String completeTodo(@PathVariable Long id) {
        TodoModel toDoModel = toDoService.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        toDoModel.setCompleted(!toDoModel.isCompleted());
        toDoService.create(toDoModel);
        return "redirect:/";
    }
}
