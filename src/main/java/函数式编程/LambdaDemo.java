package 函数式编程;

import java.util.function.Function;

/**
 * @author : pleier
 * @date : 2018/8/21
 */
public class LambdaDemo {

    public static void main(String[] args) {
        Runnable noArguments = () -> System.out.println("uf");
        noArguments.run();

        Function<String, String> upcase = String::toUpperCase;
    }
}
