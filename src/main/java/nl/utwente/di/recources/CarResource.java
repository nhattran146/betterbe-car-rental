package nl.utwente.di.recources;

import nl.utwente.di.dao.CarDao;
import nl.utwente.di.model.Car;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;

@Path("/car")
public class CarResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    String id;

    public CarResource(UriInfo uriInfo, Request request, String id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Car getCar() {
        Car car = CarDao.instance.getModel().get(id);
        if(car==null)
            throw new RuntimeException("Get: Car with " + id +  " not found");
        return car;
    }

    // for the browser
    @GET
    @Produces(MediaType.TEXT_XML)
    public Car getCarHTML() {
        Car car = CarDao.instance.getModel().get(id);
        if(car==null)
            throw new RuntimeException("Get: Todo with " + id +  " not found");
        return car;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response putTodo(JAXBElement<Car> carJAXBElement) {
        Car car = carJAXBElement.getValue();
        return putAndGetResponse(car);
    }

    @DELETE
    public void deleteTodo() {
        Car c = CarDao.instance.getModel().remove(id);
        if(c==null)
            throw new RuntimeException("Delete: Car with " + id +  " not found");
    }

    private Response putAndGetResponse(Car car) {
        Response res;
        if(CarDao.instance.getModel().containsKey(car.getId())) {
            res = Response.noContent().build();
        } else {
            res = Response.created(uriInfo.getAbsolutePath()).build();
        }
        CarDao.instance.getModel().put(car.getId(), car);
        return res;
    }
}
