package lab.model.simple;

import lab.model.Bar;
import lab.model.Customer;
import lab.model.CustomerBrokenException;
import lab.model.Squishee;
import org.springframework.stereotype.Service;

@Service
public class ApuBar implements Bar {

	public Squishee sellSquishee(Customer customer)  {
        if (customer.isBroke()){
            throw new CustomerBrokenException();
        }
        System.out.println("Here is your SimpleSquishee \n");
        return new SimpleSquishee("Usual SimpleSquishee");
    }
}