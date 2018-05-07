package pe.edu.cibertec.web.listeners;

import java.io.IOException;
import java.io.InputStream;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ApplicationListener implements ServletContextListener {
    
    private SqlSessionFactory createSqlSessionFactory() {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory
                    = new SqlSessionFactoryBuilder().build(inputStream);
            return sqlSessionFactory;
        } catch (IOException e) {
            System.out.println("Error al generar SQLSessionFactory");
            e.printStackTrace(System.out);
            return null;
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SqlSessionFactory ssf = createSqlSessionFactory();
        sce.getServletContext().setAttribute("ssf", ssf);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
