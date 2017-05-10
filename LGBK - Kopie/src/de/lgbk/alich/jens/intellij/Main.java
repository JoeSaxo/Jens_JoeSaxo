package de.lgbk.alich.jens.intellij;

import de.joesaxo.library.annotation.*;
import de.joesaxo.library.annotation.filter.AnnotationFilter;
import de.lgbk.alich.jens.ArrayNumber;

/**
 * Created by Jens on 22.04.2017.
 */
public class Main {

    private static AnnotationManager annotations;

    private static Parameter printArray = new Parameter("value", "printArray");
    private static Parameter fillArray = new Parameter("value", "fillArray");

    public static void main(String[] args) {
        init();
        sort("bubble", false);
        sort("insertion", false);
        sort("selection", false);
        sort("merge", false);
        sort("quick", false);
    }

    private static void sort(String name, boolean print) {
        annotations.invokeMethods(createModule().addParameter(fillArray));
        if (print) annotations.invokeMethods(createModule().addParameter(printArray));
        Object[] times = annotations.invokeMethods(createModule().addParameter(new Parameter("value", name + "Sort")));
        for (Object time : times) {
            System.out.println(name + "Sort: " + time + "ms");
        }
        if (print) annotations.invokeMethods(createModule().addParameter(printArray));
    }

    private static Object getCls() {
        return new ArrayNumber(1000);
    }

    private static void init() {
        annotations = new AnnotationManager();
        annotations.setClass(getCls());
    }

    private static AnnotationFilter createModule() {
        return new AnnotationFilter().setAnnotation(Method.class).addParameter(new Parameter("execute", true));
    }
}
