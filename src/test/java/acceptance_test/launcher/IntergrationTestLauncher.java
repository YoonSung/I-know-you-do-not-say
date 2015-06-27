package acceptance_test.launcher;

import org.apache.catalina.LifecycleState;
import org.apache.catalina.Server;
import org.apache.catalina.startup.Tomcat;
import org.junit.runner.JUnitCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomcat.WebServerLauncher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoon on 15. 6. 24..
 */
public class IntergrationTestLauncher {

    private static final String TEST_CLASS_PACKAGE_PATH = "acceptance_test/controller";

    private static final Logger log = LoggerFactory.getLogger(IntergrationTestLauncher.class);

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        WebServerLauncher.startServer(tomcat, () -> {
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

            startTest();
        });

        //It is not called. Because JUnit call System.exit() method in internal
        log.info("Terminate Tomcat Server");
        WebServerLauncher.stopServer(tomcat);
    }

    private static void startTest() {
        File file = new File("src/test/java/"+ TEST_CLASS_PACKAGE_PATH);

        try {
            List<String> classNameList = findClasseNames(file);
            String[] array = classNameList.toArray(new String[classNameList.size()]);

            JUnitCore.main(array);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> findClasseNames(File directory) throws ClassNotFoundException {
        List<String> classNameList = new ArrayList<>();
        if (!directory.exists())
            throw new ClassNotFoundException("TEST_CLASS_PACKAGE_PATH is wrong! {:"+ TEST_CLASS_PACKAGE_PATH +"}");

        File[] files = directory.listFiles();

        String packageExpression = TEST_CLASS_PACKAGE_PATH.replace("/", ".")+".";

        for (File file : files) {
            if (!file.isDirectory()) {
                classNameList.add((packageExpression+file.getName().replace(".java", "")));
            }
        }

        return classNameList;
    }
}
