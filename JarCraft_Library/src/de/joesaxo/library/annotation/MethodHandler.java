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
            if (!parameters[i].getClass().equals(neededParameters[i])) return false;
        }
        return true;
    }

    public static boolean isObjetctMethod(Method method) {
        Method[] methodsOfClassObject = Object.class.getDeclaredMethods();
        for (Method objectMethod : methodsOfClassObject) {
            if (method.equals(objectMethod)) return true;
        }
        return false;
    }

    public static Method[] removeObjectMethods(Method[] methods) {
        int unneededMethods = 0;
        for (Method method : methods) {
            if (isObjetctMethod(method)) unneededMethods++;
        }
        Method[] newMethods = new Method[methods.length - unneededMethods];
        int skippedMethods = 0;
        for (int i = 0; i < methods.length; i++) {
            if (!isObjetctMethod(methods[i])) {
                newMethods[i-skippedMethods] = methods[i];
            } else {
                skippedMethods++;
            }
        }
        return newMethods;
    }


    public static Method[] getMethodsFromAnnotation(Annotation annotation) {
        Method[] methods = annotation.annotationType().getDeclaredMethods();
        return removeObjectMethods(methods);
    }

}
