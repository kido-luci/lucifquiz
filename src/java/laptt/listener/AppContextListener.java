package laptt.listener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        try {
            ServletContext servletContext = servletContextEvent.getServletContext();

            InputStream inputStream = servletContext.getResourceAsStream("/WEB-INF/config.properties");

            Properties properties = new Properties();
            properties.load(inputStream);

            Set<Object> keySet = properties.keySet();
            for (Object key : keySet) {
                servletContext.setInitParameter(key.toString(), properties.getProperty(key.toString()));
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
