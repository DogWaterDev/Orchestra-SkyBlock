package de.dogwaterdev.komodoperms;



import java.lang.reflect.Method;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import java.util.Set;

import static de.dogwaterdev.skyblock.SkyBlock.log;

public class PermissionsChecker {
    public static void check(String packageName) {
        log("Initiating permissions checking...");

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(packageName)
                .addScanners(Scanners.TypesAnnotated, Scanners.MethodsAnnotated)
                .addUrls(ClasspathHelper.forPackage(packageName))
        );
        Set<Method> methods = reflections.getMethodsAnnotatedWith(RequiresPermission.class);
        log("Method count: " + String.valueOf(methods.size()));
        if (methods.isEmpty()) {
            log("No methods with @RequiresPermission found in package: " + packageName);
            return;
        }
        for (Method method : methods) {
            RequiresPermission annotation = method.getAnnotation(RequiresPermission.class);
            log(String.format(
                    "Class: %s, Method: %s, Permission needed: %s",
                    method.getDeclaringClass().getName(), method.getName(), annotation.permission()));
        }
    }
}