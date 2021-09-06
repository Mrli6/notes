import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("输入全类名:");
        String name = in.next();

        try {
            Class<?> c1 = Class.forName(name);  // Class对象
            Class<?> superc1 = c1.getSuperclass();   // 父类
            String modifiers = Modifier.toString(c1.getModifiers());    // 修饰符
            if(modifiers.length() > 0){ // 打印修饰符
                System.out.print(modifiers+" ");
            }
            System.out.print("class " + name); // 打印class关键字和全类名

            if(superc1 != null && superc1 != Object.class){ // 打印父类
                System.out.println(" extends "+superc1.getName()+" {");
            }


            Test.printFields(c1);
            Test.printConstructors(c1);
            Test.printMethods(c1);


            System.out.println("}");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void printFields(Class c1){
        Field[] fields = c1.getDeclaredFields();

        for(Field field : fields){
            String modifier = Modifier.toString(field.getModifiers()); // 属性修饰符
            System.out.print("  ");
            if(modifier.length() > 0){
                System.out.print(modifier+" ");
            }


            Class<?> type = field.getType();    // 属性类型对象即Class对象
            if(type.isArray()){ //判断是否是数组类型，因为数组类型在打印时有些小bug
                Class<?> componentType = type.getComponentType();
                System.out.print(componentType.getName()+"[] ");
            }else{
                System.out.print(type.getName()+" ");
            }


            String name = field.getName();  // 属性名称
            System.out.println(name+";");
        }
    }

    private static void printConstructors(Class c1){
        System.out.println();
        Constructor[] constructors = c1.getDeclaredConstructors();

        for(Constructor constructor : constructors){
            String modifier = Modifier.toString(c1.getModifiers()); //构造器修饰符
            System.out.print("  ");
            if(modifier.length() > 0){
                System.out.print(modifier+" ");
            }

            String name = constructor.getName();
            System.out.print(name+" (");

            Class[] parameterTypes = constructor.getParameterTypes();
            for(int i = 0; i < parameterTypes.length; i++){
                System.out.print(parameterTypes[i].getName());
                if(i < parameterTypes.length-1){
                    System.out.print(", ");
                }
            }

            System.out.println(");");
        }
    }

    private static void printMethods(Class c1){
        System.out.println();
        Method[] methods = c1.getDeclaredMethods();

        for(Method method : methods){
            System.out.print("  ");
            String modifier = Modifier.toString(method.getModifiers());
            if(modifier.length() > 0){
                System.out.print(modifier+" ");
            }

            Class<?> returnType = method.getReturnType();
            if(returnType.isArray()){
                Class<?> componentType = returnType.getComponentType();
                System.out.print(componentType.getName()+"[] ");
            }else{
                System.out.print(returnType.getName()+" ");
            }

            String name = method.getName();
            System.out.print(name+" (");

            Class<?>[] parameterTypes = method.getParameterTypes();
            for(int i = 0; i < parameterTypes.length; i++){
                if(parameterTypes[i].isArray()){
                    Class<?> componentType = parameterTypes[i].getComponentType();
                    System.out.print(componentType.getName()+"[]");
                }else{
                    System.out.print(parameterTypes[i].getName());
                }
                if(i < parameterTypes.length-1){
                    System.out.print(", ");
                }
            }

            System.out.println(");");
        }
    }
}