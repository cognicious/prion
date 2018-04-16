package cognicious.prion;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class PrionTransformer implements ClassFileTransformer {
    
    private ClassPool cp;

    public PrionTransformer(ClassPool cp) {
        this.cp = cp;
    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            
            if (className.startsWith("java/lang/invoke/")
                || className.startsWith("cognicious/prion")) {
                return null;
            }
            
            CtClass ct = cp.makeClass(new ByteArrayInputStream(classfileBuffer));            
            CtMethod[] declaredMethods = ct.getDeclaredMethods();
            for (CtMethod method : declaredMethods) {
                //if (!Modifier.isAbstract(method.getReturnType​().getModifiers​())) {
                  //  if (!method.isEmpty()) {
                    //region instrument method
                    method.insertBefore(" { " +   
                                        "cognicious.prion.PrionRegister.propagate(\"" + className + "\",\"" + method.getName() + "\"); " +
                                        //"System.out.println(\"" + className + "." + method.getName() + "\"); " +
                                        "}");
                    method.insertAfter("{ "+
                                       "cognicious.prion.PrionRegister.propagate(\"" + className + "\",\"" + method.getName() + "\"); " +
                                       " }", true);
                    //endregion
                    //}
                //}
            }
            
            return ct.toBytecode();
        } catch (Throwable e) {
            //e.printStackTrace();
            return classfileBuffer;
        }
    }
}
