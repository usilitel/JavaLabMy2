package ioc;

import lab.model.Person;
import lab.model.simple.SimplePerson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static ioc.HelloWorldTest.getExpectedPerson;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleAppTest {
	
	private static final String APPLICATION_CONTEXT_XML_FILE_NAME = "ioc.xml";

	private BeanFactory context = new ClassPathXmlApplicationContext(
			APPLICATION_CONTEXT_XML_FILE_NAME);

	private Person expectedPerson = getExpectedPerson();

	@Test
	void testInitPerson() {
//		FYI: Another way to achieve the bean person = context.getBean(SimplePerson.class);
		assertEquals(expectedPerson, context.getBean("person"));
	}
}
