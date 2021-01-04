package com.todo.app.jwt.api;


import com.todo.app.jwt.model.User;
import com.todo.app.jwt.repository.UserRepository;
import com.todo.app.jwt.util.PasswordUtils;
import com.todo.app.jwt.util.TokenUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static com.todo.app.jwt.util.SecurityConstants.EXPIRATION_TIME;
import static com.todo.app.jwt.util.SecurityConstants.TOKEN_PREFIX;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Path("/login")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class LoginController {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordUtils passwordUtils;

    @POST
    @Path("/users/token")
    @PermitAll
    public Response login(LoginUserRequest loginUserRequest) {

        User user = userRepository.findByUserName(loginUserRequest.getUserName());

        if (user == null) {
            return Response.status(BAD_REQUEST).entity("Username does not exists").build();
        }

        if (!user.getPassword().equals(passwordUtils.encode(loginUserRequest.getPassword()))) {
            return Response.status(BAD_REQUEST).entity("The password typed is incorrect").build();
        }

        String token = TokenUtils.generateUserToken(user, EXPIRATION_TIME, issuer);
        String bearerToken = String.format("%s %s", TOKEN_PREFIX, token);

        return Response.ok().entity(bearerToken).build();
    }

}
