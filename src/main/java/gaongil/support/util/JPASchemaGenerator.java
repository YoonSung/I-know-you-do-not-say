package gaongil.support.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Reference by http://blog.iprofs.nl/2013/01/29/hibernate4-schema-generation-ddl-from-annotated-entities/
 * @author yoon
 */
public class JPASchemaGenerator {
	
    private Configuration configuration;

    /**
     * @param args first argument is the directory to generate the dll to
     */
    public static void main(String[] args) throws Exception {
    	System.out.println("-------- [ SQL Generating. ]");
        final String packageName = args[0];
        JPASchemaGenerator gen = new JPASchemaGenerator(packageName);
        final String directory = args[1];
        gen.generate(Dialect.MYSQL, directory);
        //gen.generate(Dialect.ORACLE, directory);
        //gen.generate(Dialect.HSQL, directory);
        //gen.generate(Dialect.H2, directory);
        System.out.println("-------- [ SQL Generator Success. ]");
    }

    public JPASchemaGenerator(String packageName) throws Exception {
        configuration = new Configuration();
        
		configuration.setProperty("hibernate.format_sql", "true");
		configuration.setProperty("hibernate.format_sql", "false");				
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        
        configuration.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
        
        for (Class<?> clazz : getClasses(packageName)) {
            configuration.addAnnotatedClass(clazz);
        }
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
     *
     * @param dialect to use
     */
    private void generate(Dialect dialect, String directory) {
        configuration.setProperty("hibernate.dialect", dialect.getDialectClass());
        SchemaExport export = new SchemaExport(configuration);
        export.setDelimiter(";");
        export.setOutputFile(directory + "ddl_" + dialect.name().toLowerCase() + ".sql");
        export.setFormat(true);
        export.execute(true, false, false, false);
    }

    /**
     * Holds the classnames of hibernate dialects for easy reference.
     */
    private static enum Dialect {
        ORACLE("org.hibernate.dialect.Oracle10gDialect"),
        MYSQL("org.hibernate.dialect.MySQLDialect"),
        HSQL("org.hibernate.dialect.HSQLDialect"),
        H2("org.hibernate.dialect.H2Dialect");

        private String dialectClass;

        private Dialect(String dialectClass) {
            this.dialectClass = dialectClass;
        }

        public String getDialectClass() {
            return dialectClass;
        }
    }
}