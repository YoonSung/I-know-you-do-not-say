package acceptance_test.launcher;

import org.apache.catalina.LifecycleState;
import org.apache.catalina.Server;
import org.apache.catalina.startup.Tomcat;
import org.junit.runner.JUnitCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomcat.WebServerLauncher;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoon on 15. 6. 24..
 */
public class IntergrationTestLauncher {

    private static final String TEST_CLASS_PACKAGE_PATH = "acceptance_test/controller";
    private static final Logger log = LoggerFactory.getLogger(IntergrationTestLauncher.class);

    public static void main(String[] args) {

        org.h2.tools.Server dbServer = null;
        Tomcat tomcat = null;

        try {
            //DB Server
            //another options (ex new String[]{"-tcp", "-tcpPort", ...})
            //http://www.h2database.com/html/advanced.html?highlight=tcpPort&search=tcpPort#firstFound
            dbServer = org.h2.tools.Server.createTcpServer();

            //Web Server Container
            tomcat = new Tomcat();
            startTestWithServer(tomcat, dbServer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("End Intergration Test");
    }

    private static void dbStart(org.h2.tools.Server server) {
        new Thread(() -> {
            try {
                server.start();
            } catch (SQLException e) {
                log.error("DB Server Start Exception : {}", e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    private static void dbShutdown(org.h2.tools.Server server) {
        if (server == null)
            return;

        if (!server.isRunning(false))
            return;
        else {
            server.stop();
            server.shutdown();
        }
    }

    private static void startTestWithServer(Tomcat tomcat, org.h2.tools.Server dbServer) throws Exception {

        WebServerLauncher.startServer(tomcat, () -> {
            dbStart(dbServer);
            waitForTomcatStart(tomcat);

            try {
                startTest();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            //It is not called. Because JUnit call System.exit() method in internal
            } finally {
                log.info("Terminate Tomcat Server");
                WebServerLauncher.stopServer(tomcat);

                log.info("Terminate Database Server");
                dbShutdown(dbServer);
            }
        });
    }

    private static void waitForTomcatStart(Tomcat tomcat) {
        Server server = tomcat.getServer();

        LifecycleState state = server.getState();
        while (state != LifecycleState.STARTED) {
            log.info("Waiting Server Started Time...Current State : {}", server.getState());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (state == LifecycleState.FAILED) {
                log.error("Tomcat Server Started Failure");
                break;
            }

            state = server.getState();
        }
    }

    private static void startTest() throws ClassNotFoundException {

        File file = new File("src/test/java/"+ TEST_CLASS_PACKAGE_PATH);

        executeJUnitMain(file);
        //executeJUnitRunClasses(file);
    }

    private static void executeJUnitRunClasses(File file) throws ClassNotFoundException {
        List<Class> classList = findTargetClasses(file);
        classList.forEach(JUnitCore::runClasses);
    }

    private static void executeJUnitMain(File file) throws ClassNotFoundException {
        List<String> classNameList = findClasseNames(file);
        String[] array = classNameList.toArray(new String[classNameList.size()]);
        JUnitCore.main(array);
    }


    private static List<String> findClasseNames(File directory) throws ClassNotFoundException {
        List<String> classNameList = new ArrayList<>();

        if (!directory.exists())
            throw new ClassNotFoundException("TEST_CLASS_PACKAGE_PATH is wrong! {:"+ TEST_CLASS_PACKAGE_PATH +"}");

        File[] files = directory.listFiles();

        String packageExpression = TEST_CLASS_PACKAGE_PATH.replace("/", ".")+".";

        for (File file : files) {
            if (!file.isDirectory()) {
                classNameList.add((packageExpression + file.getName().replace(".java", "")));
            }
        }

        return classNameList;
    }


    private static List<Class> findTargetClasses(File directory) throws ClassNotFoundException {
        List<Class> classList = new ArrayList<>();
        if (!directory.exists())
            throw new ClassNotFoundException("TEST_CLASS_PACKAGE_PATH is wrong! {:"+ TEST_CLASS_PACKAGE_PATH +"}");

        File[] files = directory.listFiles();

        String packageExpression = TEST_CLASS_PACKAGE_PATH.replace("/", ".")+".";

        for (File file : files) {
            if (!file.isDirectory()) {
                Class targetClass = Class.forName((packageExpression + file.getName().replace(".java", "")));
                classList.add(targetClass);
                //classList.add());
            }
        }

        return classList;
    }
}
