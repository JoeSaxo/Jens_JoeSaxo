package de.joesaxo.library.annotation;

import de.joesaxo.library.array.Array;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * Created by Jens on 17.04.2017.
 */
public class MethodHandler {

    public static Object invokeMethod(Method method, Object object, Object[] parameters) {
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return null;
    }

    public static Object invokeMethod(Method method, Object object, Object parameter) {
        return invokeMethod(method, object, new Object[]{parameter});
    }

    public static Object invokeMethod(Method method, Object object) {
        return invokeMethod(method, object, new Object[]{});
    }

    public static boolean isInvokableMethod(Method method, Object[] parameters) {
        Type[] neededParameters = method.getGenericParameterTypes();
        if (neededParameters.length != parameters.length) return false;

        for (int i = 0; i < parameters.length; i++) {
            /*Class<?> cls = parameters[i].getClass();
            boolean matches;
            do {
                matches = cls.equals(neededParameters[i]);
                cls = cls.getSuperclass();
            } while (!matches || cls != null);
*/
            //if (!parameters[i].getClass().equals(neededParameters[i])) return false;
            //*/
            if (!checkMethodParameters(parameters[i].getClass(), neededParameters[i])) return false;
        }
        return true;
    }

    public static boolean checkMethodParameters(Class<?> firstClass, Type secondClass) {

        if (firstClass.equals(secondClass)) return true;

        for (Class<?> newFirstClass : firstClass.getInterfaces()) {
            if (checkMethodParameters(newFirstClass, secondClass)) return true;
        }

        if (checkMethodParameters(firstClass.getSuperclass(), secondClass)) return true;

        return false;
    }


    public static Method[] getMethodsFromAnnotation(Annotation annotation) {
        return annotation.annotationType().getDeclaredMethods();
    }

    public static Method[] getInvocableMethods(Method[] methods, Object[] parameters) {
        int unneededMethods = 0;
        for (Method method : methods) {
            if (!isInvokableMethod(method, parameters)) unneededMethods++;
        }
        Method[] newMethods = new Method[methods.length - unneededMethods];
        int skippedMethods = 0;
        for (int i = 0; i < methods.length; i++) {
            if (isInvokableMethod(methods[i], parameters)) {
                newMethods[i-skippedMethods] = methods[i];
            } else {
                skippedMethods++;
            }
        }
        return newMethods;
    }

}
