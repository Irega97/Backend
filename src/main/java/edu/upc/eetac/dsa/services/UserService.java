package edu.upc.eetac.dsa.services;


import edu.upc.eetac.dsa.UserManager;
import edu.upc.eetac.dsa.UserManagerImpl;
import edu.upc.eetac.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

//EN VEZ DE HACER CONSULTAS A LA INSTANCIA, AQUI DEBERE CONSULTARLO EN LA BBDD

@Api(value = "/usermanager", description = "Endpoint to User Service")
@Path("/usermanager")
public class UserService {

    private UserManager um;

    public UserService() {
        this.um = UserManagerImpl.getInstance();
        if (um.size()==0) {
            this.um.addUser("Ivan","ivan@yahoo.es", "jsdjj");
            this.um.addUser("Manu", "manu@outlook.es", "jdjfj");
        }
    }

    @GET
    @ApiOperation(value = "get all Users", notes = "Devuelve una lista con todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {

        List<User> users = null;

        users = this.um.getAllUsers();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};

        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get a User", notes = "Devuelve el usuario que tenga el id que quieras buscar")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) { //PORQUE NO VA SI ID = /OSU?N ?????
        User u = this.um.getUser(id);
        if (u == null) return Response.status(404).build();
        else  return Response.status(201).entity(u).build();
    }

    @DELETE
    @ApiOperation(value = "delete a User", notes = "Elimina un usuario mediante su id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        User u = this.um.getUser(id);
        if (u == null) return Response.status(404).build();
        else this.um.deleteUser(id);
        return Response.status(201).build();
    }

    @PUT
    @ApiOperation(value = "update a User", notes = "Actualiza los datos de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/")
    public Response updateUser(User user) {

        User u = this.um.updateUser(user);

        if (u == null) return Response.status(404).build();

        return Response.status(201).build();
    }



    @POST
    @ApiOperation(value = "create a new User", notes = "Crea un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/{nombre}/{mail}/{password}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(@PathParam("nombre") String nombre, @PathParam("mail") String mail, @PathParam("password") String password) {

        User user = new User(nombre,mail,password);
        if(user.getMail()==null || user.getNombre()==null) return Response.status(500).entity(user).build();
        this.um.addUser(user.getNombre(), user.getMail(), user.getPassword());
        return Response.status(201).entity(user).build();
    }

}