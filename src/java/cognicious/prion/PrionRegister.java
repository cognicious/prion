package cognicious.prion;

import clojure.lang.RT;
import clojure.lang.Symbol;

public class PrionRegister {
    
    public static void propagate(String className, String methodName) {
        try {
            RT.var("clojure.core", "require").invoke(Symbol.intern("cognicious.prion.state"));
            RT.var("cognicious.prion.state", "register").invoke(className, methodName);
            //System.out.println("prion> " + className + "." + methodName + " = " + x);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

}
