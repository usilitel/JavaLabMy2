package aop;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import commons.TestUtils;
import lab.model.Customer;
import lab.model.simple.ApuBar;
import lab.model.Bar;

import lab.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:aop.xml")
class AopAspectJTest {

	@Autowired
    private Bar bar;
    
	@Autowired
    private Customer customer;

    private String systemOut = TestUtils.fromSystemOut(() ->
            bar.sellSquishee(customer));

    @Test
    void testBeforeAdvice() {
        assertTrue("Before advice is not good enough...",
                systemOut.contains("Hello"));

        assertTrue("Before advice is not good enough...",
                systemOut.contains("How are you doing?"));
    }

    @Test
    void testAfterAdvice() {
        assertTrue("After advice is not good enough...",
                systemOut.contains("Good Bye!"));
    }

    @Test
    void testAfterReturningAdvice() {
        assertTrue("Customer is broken",
                systemOut.contains("Good Enough?"));
    }

    @Test
    void testAroundAdvice() {
        assertTrue("Around advice is not good enough...",
                systemOut.contains("Hi!"));

        assertTrue("Around advice is not good enough...",
                systemOut.contains("See you!"));
    }

    @Test
    void testAllAdvices() {
        assertFalse(bar instanceof ApuBar,
                "barObject instanceof ApuBar");
    }
}