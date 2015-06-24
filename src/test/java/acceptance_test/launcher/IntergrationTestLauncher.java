package acceptance_test.launcher;

import org.junit.runner.JUnitCore;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoon on 15. 6. 24..
 */
public class IntergrationTestLauncher {

    private static final String PACKAGE_PATH = "acceptance_test/controller";


    public static void main(String[] args) {
        //JUnitCore.main(new String[]{"acceptance_test.controller.UserControllerTest"});
        File file = new File("src/test/java/"+PACKAGE_PATH);

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
            throw new ClassNotFoundException("PACKAGE_PATH is wrong! {:"+PACKAGE_PATH+"}");

        File[] files = directory.listFiles();

        String packageExpression = PACKAGE_PATH.replace("/", ".")+".";

        for (File file : files) {
            if (!file.isDirectory()) {
                classNameList.add((packageExpression+file.getName().replace(".java", "")));
            }
        }

        return classNameList;
    }
}
