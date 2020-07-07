package mvcrest.user;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import mvcrest.authentication.AuthService;


@Path("/users")
public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password ) throws IOException {
        User user = userService.findUser(username,password);
        if(user != null) {
            user.setJWTToken(AuthService.generateJWT(user));
            return Response.ok(user).build();
        }
        return Response.status(404,"User is not valid!").build();
    }

    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@QueryParam("username") String username, @QueryParam("password") String password ) throws IOException {
        User user = userService.findUser(username,password);
        if(user != null) {
            user.setJWTToken("");
            return Response.ok(user).build();
        }
        return Response.status(404).build();
    }

    /**
     * Dohvatanje svih User-a
     * Putanja je rest/users
     * <p>
     * Takodje je moguce isfiltrirati korisnike po imenu.
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON) //Bitno je da navedemo sta je rezultujuci content type ove metode
    public Response getUsers(@HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            List<User> list =  userService.getUsers();
            if(list != null){
                return Response.ok(list).build();
            }
        }
        return Response.status(404).build();
    }


//VRACA BROJ REZERVACIJA USERA
    @GET
    @Path("/bookingnum/{id}")
    @Produces(MediaType.APPLICATION_JSON) //Bitno je da navedemo sta je rezultujuci content type ove metode
    public Response getNumberOfReservations(@PathParam("id") int id, @HeaderParam("Authorization") String auth) throws IOException {
        System.out.println(auth);
        if(AuthService.isAuthorized(auth)) {
            int number = userService.getNumberOfReservations(id);
            if (number >= 0) {
                return Response.ok(number).build();
            }
        }
        return Response.status(404).build();
    }


    /**
     * Dohvatanje jednog resursa po ID-u.
     * Putanja bi bila npr. rest/users/4
     *
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id,@HeaderParam("Authorization") String auth) throws IOException { // Da bi se u tacan argument prosledio id mora da se oznaci anotacijom
        if(AuthService.isAuthorized(auth)) {
        User u = userService.getUserById(id);
            if (u != null) {
                return Response.ok(u).build();
            }
        }
        return Response.status(404).build();
    }



//    public User deleteUserById(Integer id) throws IOException {
//        return UserRepository.deleteUserById(id);
//    }


    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(User user,@HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            User u = userService.findUserByUsername(user.getUsername());
            if (u == null) {
                User added = userService.addUser(user);
                if(added != null){
                    return Response.ok(u).build();
                }
            }
        }
        return Response.status(404).build();
    }

    /**
     * Cuvanje jednog resursa. Rezultat je taj resurs sa ID-em.
     * Putanja je rest/users
     *
     * @param user
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user, @HeaderParam("Authorization") String auth) throws IOException {
        if(AuthService.isAuthorized(auth)) {
            User u = userService.addUser(user);
            if(u != null){
                return Response.ok(u).build();
            }
        }
        return Response.status(404).build();
    }
}
