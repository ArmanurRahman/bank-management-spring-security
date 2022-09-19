package net.armanit.springsecuritydemo.config;

import net.armanit.springsecuritydemo.model.Customer;
import net.armanit.springsecuritydemo.model.SecurityCustomer;
import net.armanit.springsecuritydemo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectUserDetails implements UserDetailsService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Customer> customers = customerRepository.findByEmail(username);
        if (customers.size() == 0) {
            throw new UsernameNotFoundException("User details not found: " + username);
        }
        return new SecurityCustomer(customers.get(0));
    }
}
