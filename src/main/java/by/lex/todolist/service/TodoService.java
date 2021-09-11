package by.lex.todolist.service;

import by.lex.todolist.persist.entity.TodoList;
import by.lex.todolist.persist.repo.TodoListRepository;
import by.lex.todolist.persist.repo.TodoRepository;
import by.lex.todolist.persist.repo.UserRepository;
import by.lex.todolist.model.TodoModel;
import by.lex.todolist.security.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import by.lex.todolist.persist.entity.Todo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoListRepository todoListRepository;
    private final UserRepository userRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository, TodoListRepository todoListRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.todoListRepository = todoListRepository;
        this.userRepository = userRepository;
    }

    public Optional<TodoModel> findById(Long id) {
        return todoRepository.findById(id)
                .map(TodoModel::new);
    }

    public List<TodoModel> findToDoByUsername(String username) {
        return todoRepository.findToDoByUser_Username(username);
    }

    public void create(TodoModel toDoModel) {
        Utils.getCurrentUser()
                .flatMap(userRepository::getUserByUsername)
                .ifPresent(user -> {
                    Todo toDo = new Todo();
                    toDo.setId(toDoModel.getId());
                    toDo.setDescription(toDoModel.getDescription());
                    toDo.setTargetDate(toDoModel.getTargetDate());
                    toDo.setUser(user);
                    toDo.setCompleted(toDoModel.isCompleted());
                    TodoList todoList = new TodoList(toDoModel.getTodoListName());
                    toDo.setTodoList(todoList);
                    todoListRepository.save(todoList);
                    todoRepository.save(toDo);
                });
    }

    public void delete(Long id) {
        todoRepository.findById(id)
                .ifPresent(todoRepository::delete);
    }
}