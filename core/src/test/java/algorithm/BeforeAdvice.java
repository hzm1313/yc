package algorithm;

/**
 * Created by DT167 on 2018/7/16.
 */
public class BeforeAdvice implements Advice{
    @Override
    public void exec() {
        System.out.println("我被命名Before");
    }
}
