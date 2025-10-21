package nl.utwente.di.recources;

import nl.utwente.di.dao.CarDao;
import nl.utwente.di.model.Car;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/cars")
public class CarsResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    // Return the list of todos to the user in the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public java.util.List<Car> getCarsBrowser() {
        List<Car> cars = new ArrayList<Car>();
        cars.addAll(CarDao.instance.getModel().values());
        return cars;
    }

    // Return the list of todos for applications
    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();
        cars.addAll(CarDao.instance.getModel().values());
        return cars;
    }

    // retuns the number of todos
    // use http://localhost:8080/de.vogella.jersey.todo/rest/todos/count
    // to get the total number of records
    @GET
    @Path("/size")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        int count = CarDao.instance.getModel().size();
        return String.valueOf(count);
    }


    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newCar(@FormParam("id") String id,
                       @FormParam("color") String color,
                       @FormParam("price") String price,
                       @FormParam("model") String model,
                       @Context HttpServletResponse servletResponse) throws IOException {
        Car car = new Car(id, color, price, model);
        if (color !=null){
            car.setModel(color);
        }
        if (price !=null){
            car.setPrice(price);
        }
        if (model !=null){
            car.setModel(model);
        }
        CarDao.instance.getModel().put(id, car);

        servletResponse.sendRedirect("../index.html");
    }

    // Defines that the next path parameter after todos is
    // treated as a parameter and passed to the TodoResources
    // Allows to type http://localhost:8080/de.vogella.jersey.todo/rest/todos/1
    // 1 will be treaded as parameter car and passed to CarResource
    @Path("{car}")
    public CarResource getCar(@PathParam("car") String id) {
        return new CarResource(uriInfo, request, id);
    }
}
