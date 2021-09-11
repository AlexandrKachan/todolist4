package by.lex.todolist.service;

import by.lex.todolist.model.TodoModel;
import by.lex.todolist.persist.entity.Todo;
import by.lex.todolist.persist.entity.TodoList;
import by.lex.todolist.persist.entity.User;
import by.lex.todolist.persist.repo.TodoListRepository;
import by.lex.todolist.persist.repo.TodoRepository;
import by.lex.todolist.persist.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
//@Sql(scripts = "classpath:test_data.sql")//todo might be used after modifying data
public class TodoServiceIT {

	@Autowired
	private TodoListRepository todoListRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
	private TodoService underTest;

    @Test
    public void shouldFindAllTodosByUsername() {
		//given
		String markTodoListName = "some mark list";
		String markTodoDescription = "mark needs to do something";
		String markName = "Mark";
		String markPass = "somePassword";

		//and
		String tomTodoListName = "some tom list";
		String tomTodoDescription = "Tom needs to do something";
		String tomName = "Tom";
		String tomPass = "someOtherPassword";

		//and data is prepared
		prepareDataWith(markTodoListName, markTodoDescription, markName, markPass);
		prepareDataWith(tomTodoListName, tomTodoDescription, tomName, tomPass);

		//and result should be
		TodoModel expectedTodo = new TodoModel();
		expectedTodo.setUsername(tomName);
		expectedTodo.setDescription(tomTodoDescription);
		expectedTodo.setTodoListName(tomTodoListName);

		List<TodoModel> expectedResult = Collections.singletonList(expectedTodo);

		//when
		List<TodoModel> result = underTest.findToDoByUsername(tomName);

		//then
		assertThat(result).isEqualTo(expectedResult);
    }

	private void prepareDataWith(String todoListName, String todoDescription, String userName, String userPass) {
		User user = new User();
		user.setUsername(userName);
		user.setPassword(userPass);
		userRepository.save(user);

		TodoList todoList = new TodoList(todoListName);
		todoListRepository.save(todoList);

		Todo todo = new Todo();
		todo.setUser(user);
		todo.setDescription(todoDescription);
		todo.setTodoList(todoList);
		todoRepository.save(todo);
	}

}
