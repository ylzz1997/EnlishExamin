import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by lhy on 2018/6/11.
 */
public class HibenateConfig {
    private static SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}
