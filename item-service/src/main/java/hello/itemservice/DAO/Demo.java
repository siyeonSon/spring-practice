package hello.itemservice.DAO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "table_demo")
@Entity
public class Demo {

    @Id @GeneratedValue
    private Long id;

    private String demoText;

}