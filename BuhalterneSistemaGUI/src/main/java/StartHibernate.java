import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StartHibernate {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MyUnit");
    }
}