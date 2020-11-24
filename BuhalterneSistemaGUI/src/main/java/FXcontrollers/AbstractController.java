package FXcontrollers;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class AbstractController {
    protected EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MyUnit");
}
