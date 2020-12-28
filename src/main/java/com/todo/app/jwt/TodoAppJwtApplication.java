package com.todo.app.jwt;

import com.todo.app.jwt.model.Role;
import com.todo.app.jwt.model.User;
import com.todo.app.jwt.repository.RoleRepository;
import com.todo.app.jwt.repository.UserRepository;
import com.todo.app.jwt.util.PasswordUtils;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.core.Application;
import java.util.ArrayList;
import java.util.List;

import static com.todo.app.jwt.model.Role.*;

public class TodoAppJwtApplication extends Application {

    @Inject
    RoleRepository roleRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordUtils passwordUtils;

    public void onStart(@Observes StartupEvent ev) {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(ADMIN));
            roleRepository.save(new Role(CUSTOMER));
            roleRepository.save(new Role(USER));
        }

        if (userRepository.count() == 0) {
            User admin = new User();

            admin.setUserName("admin");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordUtils.encode("admin"));

            admin.setRoles(roleRepository.findAll());
            userRepository.save(admin);

            User user = new User();

            user.setUserName("user");
            user.setEmail("user@user.com");
            user.setPassword(passwordUtils.encode("user"));

            // Only User role
            List<Role> userRoles = new ArrayList<>();
            Role userRole = roleRepository.findByName(USER);
            userRoles.add(userRole);
            user.setRoles(userRoles);
            userRepository.save(user);


            User customer = new User();

            customer.setUserName("customer");
            customer.setEmail("customer@customer.com");
            customer.setPassword(passwordUtils.encode("customer"));

            // Only User role
            List<Role> customerRoles = new ArrayList<>();
            Role customerRole = roleRepository.findByName(CUSTOMER);
            customerRoles.add(customerRole);
            customer.setRoles(customerRoles);
            userRepository.save(customer);

        }
    }
}
