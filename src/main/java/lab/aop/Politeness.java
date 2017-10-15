package lab.aop;

import lab.model.Customer;
import lab.model.Squishee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Politeness {

    @Pointcut("execution(* sellSquishee(..))")
    private void sellSquishee() {
    }

    @Before("sellSquishee()")
    public void sayHello(JoinPoint joinPoint) {
        System.out.printf("Hello %s. How are you doing?%n",
                ((Customer) joinPoint.getArgs()[0])
                        .getName());
    }

    @AfterReturning(
            pointcut = "sellSquishee()",
            returning = "retVal") // , argNames = "retVal"
    public void askOpinion(Squishee retVal) {
        System.out.printf("Is %s Good Enough?%n", retVal.getName());
    }

    @AfterThrowing("sellSquishee()")
    public void sayYouAreNotAllowed() {
        System.out.println("Hmmm...");
    }

    @After("sellSquishee()")
    public void sayGoodBye() {
        System.out.println("Good Bye!");
    }

    @Around("sellSquishee()")
    public Object sayPoliteWordsAndSell(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Hi!");
        Object retVal = pjp.proceed();
        System.out.println("See you!");
        return retVal;
    }

}
