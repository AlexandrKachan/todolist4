package by.lex.todolist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import by.lex.todolist.persist.entity.Todo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TodoModel {

    private Long id;

    @NotEmpty
    private String description;

    private String username;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

    @Value("false")
    private boolean isCompleted;

    @NotEmpty
    private String todoListName;

    public TodoModel(Todo toDo) {
        this.id = toDo.getId();
        this.description = toDo.getDescription();
        this.targetDate = toDo.getTargetDate();
        this.username = toDo.getUser().getUsername();
        this.isCompleted = toDo.isCompleted();
        this.todoListName = toDo.getTodoList().getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TodoModel)) return false;
        TodoModel todoModel = (TodoModel) o;
        return isCompleted == todoModel.isCompleted && Objects.equals(description, todoModel.description) && Objects.equals(username, todoModel.username) && Objects.equals(targetDate, todoModel.targetDate) && Objects.equals(todoListName, todoModel.todoListName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, username, targetDate, isCompleted, todoListName);
    }
}
