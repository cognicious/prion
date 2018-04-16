package cognicious.prion;

import clojure.lang.RT;
import clojure.lang.Symbol;

public class PrionRegister {
    
    public static void begin(String className, String methodName) {
        RT.var("clojure.core", "require").invoke(Symbol.intern("cognicious.prion.state"));
        Object x = RT.var("cognicious.prion.state", "oko").invoke(1);
        System.out.println("prion> " + className + "." + methodName + " = " + x);
    }

}
