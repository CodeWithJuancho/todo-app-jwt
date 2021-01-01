package com.todo.app.jwt.api;

import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import static com.todo.app.jwt.model.Role.*;


@Path("/secured")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TokenSecuredController {

    // Interface that provides access to the claims associated with the current authenticated token.
    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/permit-all")
    @PermitAll
    // Common security annotation that indicates that the given endpoint is accessible by any caller, authenticated or not.
    public String permitAll(@Context SecurityContext context) { // JAX-RS SecurityContext to inspect the security state of the call
        return getResponseString(context);
    }

    @GET
    @Path("/roles-allowed")
    @RolesAllowed({ADMIN, USER, CUSTOMER})
    // Common security annotation that indicates that the given endpoint is accessible by a caller if they have either a "User" or "Admin" role assigned.
    public String rolesAllowed(@Context SecurityContext context) { // JAX-RS SecurityContext to inspect the security state of the call
        return getResponseString(context);
    }

    private String getResponseString(SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }
        return String.format("Hello %s,"
                        + " isHttps: %s,"
                        + " authScheme: %s,"
                        + " hasJWT: %s",
                name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt());
    }

    private boolean hasJwt() {
        return jwt.getClaimNames() != null;
    }
}