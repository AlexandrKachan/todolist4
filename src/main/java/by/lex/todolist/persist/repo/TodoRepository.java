package by.lex.todolist.persist.repo;

import by.lex.todolist.persist.entity.User;
import by.lex.todolist.model.TodoModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import by.lex.todolist.persist.entity.Todo;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

    List<TodoModel> findToDoByUser_Username(String username);

    List<TodoModel> findToDoByUser(User user);
}