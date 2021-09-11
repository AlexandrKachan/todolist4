package by.lex.todolist.persist.repo;

import by.lex.todolist.persist.entity.TodoList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodoListRepository extends CrudRepository<TodoList, String> {

}
