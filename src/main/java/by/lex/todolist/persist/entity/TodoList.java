package by.lex.todolist.persist.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "todo_list")
public class TodoList {

    @Id
    @Column(
            unique = true,
            nullable = false
    )
    private String name;

    public TodoList(String name) {
        this.name = name;
    }
}
