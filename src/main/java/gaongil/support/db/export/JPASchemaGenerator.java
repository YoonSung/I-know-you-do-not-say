package gaongil.support.db.export;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sun.javafx.binding.StringFormatter;
import gaongil.support.Constant;
import javafx.scene.effect.Reflection;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.core.io.ClassPathResource;

/**
 * Reference by http://blog.iprofs.nl/2013/01/29/hibernate4-schema-generation-ddl-from-annotated-entities/
 * @author yoon
 */
public class JPASchemaGenerator {

    private static Properties properties = new Properties();

    private Configuration configuration;

    /**
     * @param args first argument is the directory to generate the dll to
     */
    public static void main(String[] args) throws Exception {
        loadProperties();

        System.out.println("-------- [ SQL Generating. ]");
        final String packageName = args[0];
        JPASchemaGenerator gen = new JPASchemaGenerator(packageName);

        //TODO Extract base directory to property
        final String directory = args[1];
        gen.generate(directory);

        System.out.println(String.format("-------- [ SQL Generator in  %s]", directory));
    }

    private static void loadProperties() throws IOException {
        ClassPathResource resource = new ClassPathResource("database.properties");
        properties.load(new FileInputStream(resource.getFile()));
    }

    public JPASchemaGenerator(String packageName) throws Exception {
        configuration = new Configuration();
        
		configuration.setProperty(Constant.PROPERTY_KEY_DB_FORMATSQL, properties.getProperty(Constant.PROPERTY_KEY_DB_FORMATSQL));
        configuration.setProperty(Constant.PROPERTY_KEY_DB_JPATODDL, properties.getProperty(Constant.PROPERTY_KEY_DB_JPATODDL));
        //configuration.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
        configuration.setNamingStrategy(getNamingStrategy());


        //TODO recursive Directory Search
        for (Class<?> clazz : getClasses(packageName)) {
            configuration.addAnnotatedClass(clazz);
        }
    }

    /**
     * @return .properties namingStrategy Class
     */
    @SuppressWarnings("deprecation")
    public NamingStrategy getNamingStrategy() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String namingStrategyPackage = properties.getProperty(Constant.PROPERTY_KEY_DB_NAMING_STRATEGY);
        Class<NamingStrategy> claz = (Class<NamingStrategy>) Class.forName(namingStrategyPackage);

        return claz.newInstance();
    }

    /**
     * Utility method used to fetch Class list based on a package name.
     * @param packageName (should be the package containing your annotated beans.
     */
    @SuppressWarnings("rawtypes")
	private List<Class> getClasses(String packageName) throws Exception {
        File directory = null;
        try {
            ClassLoader cld = getClassLoader();
            URL resource = getResource(packageName, cld);
            directory = new File(resource.getFile());
        } catch (NullPointerException ex) {
            throw new ClassNotFoundException(packageName + " (" + directory
                    + ") does not appear to be a valid package");
        }
        return collectClasses(packageName, directory);
    }

    private ClassLoader getClassLoader() throws ClassNotFoundException {
        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        if (cld == null) {
            throw new ClassNotFoundException("Can't get class loader.");
        }
        return cld;
    }

    private URL getResource(String packageName, ClassLoader cld) throws ClassNotFoundException {
        String path = packageName.replace('.', '/');
        URL resource = cld.getResource(path);
        if (resource == null) {
            throw new ClassNotFoundException("No resource for " + path);
        }
        return resource;
    }

    @SuppressWarnings("rawtypes")
	private List<Class> collectClasses(String packageName, File directory) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (directory.exists()) {
            String[] files = directory.list();
            for (String file : files) {
                if (file.endsWith(".class")) {
                    // removes the .class extension
                    classes.add(Class.forName(packageName + '.'
                            + file.substring(0, file.length() - 6)));
                }
            }
        } else {
            throw new ClassNotFoundException(packageName
                    + " is not a valid package");
        }
        return classes;
    }

    /**
     * Method that actually creates the file.
     */
    private void generate(String directory) {
        String dialect = properties.getProperty(Constant.PROPERTY_KEY_JPA_DIALECT);
        configuration.setProperty(Constant.PROPERTY_KEY_JPA_DIALECT, dialect);
        SchemaExport export = new SchemaExport(configuration);
        export.setDelimiter(";");
        export.setOutputFile(directory + "schema.sql");
        export.setFormat(true);
        export.execute(true, false, false, false);
    }
}