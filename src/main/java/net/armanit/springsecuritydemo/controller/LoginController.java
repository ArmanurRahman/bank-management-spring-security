package net.armanit.springsecuritydemo.controller;

import lombok.extern.slf4j.Slf4j;
import net.armanit.springsecuritydemo.model.Customer;
import net.armanit.springsecuritydemo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Principal user) {
        log.info("inside login");
        List<Customer> customers = customerRepository.findByEmail(user.getName());

        if (customers.size() > 0) {
            return customers.get(0);
        }else {
            return null;
        }

    }
}
