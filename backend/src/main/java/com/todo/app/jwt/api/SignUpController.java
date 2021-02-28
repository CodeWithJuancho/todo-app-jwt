package com.todo.app.jwt.api;


import com.todo.app.jwt.model.User;
import com.todo.app.jwt.repository.RoleRepository;
import com.todo.app.jwt.repository.UserRepository;
import com.todo.app.jwt.util.PasswordUtils;
import org.apache.commons.validator.routines.EmailValidator;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Collections;

import static com.todo.app.jwt.model.Role.USER;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Path("/sign-up")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class SignUpController {

    @Inject
    UserRepository userRepository;

    @Inject
    RoleRepository roleRepository;

    @Inject
    PasswordUtils passwordUtils;

    @POST
    @Path("/user")
    @PermitAll
    public Response login(SignUpUserRequest signUpUserRequest) {
        if (userRepository.findByUserName(signUpUserRequest.getUserName()) != null) {
            return Response.status(BAD_REQUEST).entity("Username already exists").build();
        }

        if (userRepository.findByEmail(signUpUserRequest.getEmail()) != null) {
            return Response.status(BAD_REQUEST).entity("Email already exists").build();
        }

        if (!EmailValidator.getInstance().isValid(signUpUserRequest.getEmail())) {
            return Response.status(BAD_REQUEST).entity("Email not valid").build();
        }

        if (!passwordUtils.validate(signUpUserRequest.getPassword())) {
            return Response.status(BAD_REQUEST).entity("Password not valid").build();
        }

        User user = new User();

        user.setUserName(signUpUserRequest.getUserName());
        user.setEmail(signUpUserRequest.getEmail());
        user.setPassword(passwordUtils.encode(signUpUserRequest.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.findByName(USER)));

        userRepository.save(user);

        return Response.ok().build();
    }

}
