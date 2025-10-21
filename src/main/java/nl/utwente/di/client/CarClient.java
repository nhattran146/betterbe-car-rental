package nl.utwente.di.client;

import nl.utwente.di.model.Car;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class CarClient {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(getBaseURI());
        String url_pattern = "Car";
        String path = "Cars";

        // create one todo
        Car car = new Car("3", "red", "2000", "supercar");

        System.out.println("URL: " + url_pattern + "/" + path + "/" + car.getId());

        Response response = target.path(url_pattern).path(path)
                .path(car.getId()).request(MediaType.APPLICATION_XML)
                .put(Entity.entity(car, MediaType.APPLICATION_XML));
        // Return code should be 201 == created resource
        System.out.println(response.getStatus());
        // Get the Todos
        System.out.println(target.path(url_pattern).path(path)
                .request(MediaType.TEXT_XML).get(String.class));
        // Get JSON for application
        System.out.println(target.path(url_pattern).path(path)
                .request(MediaType.APPLICATION_JSON).get(String.class));
        // Get XML for application
        System.out.println(target.path(url_pattern).path(path)
                .request(MediaType.APPLICATION_XML).get(String.class));

        // Get the Todo with id 1
        System.out.println(target.path(url_pattern).path(path + "/1")
                .request(MediaType.APPLICATION_XML).get(String.class));
        // get Todo with id 1
        target.path(url_pattern).path(path + "/1").request().delete();
        // Get the all todos, id 1 should be deleted
        System.out.println(target.path(url_pattern).path(path)
                .request(MediaType.APPLICATION_XML).get(String.class));

        // create a Todo
        Form form = new Form();
        form.param("id", "4");
        form.param("summary", "Demonstration of the client lib for forms");
        response = target.path(url_pattern).path(path).request(MediaType.TEXT_HTML)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        System.out.println("Form response: " + response.readEntity(String.class));
        // Get the all todos, id 4 should be created
        System.out.println(target.path("rest").path("todos")
                .request(MediaType.APPLICATION_XML).get(String.class));

        // Return to initial situation
        target.path(url_pattern).path(path + "/3").request().delete();
        target.path(url_pattern).path(path + "/4").request().delete();
        // Get the all todos, ids 3 and 4 should be deleted
        System.out.println(target.path("rest").path("todos")
                .request(MediaType.APPLICATION_XML).get(String.class));

        // Create new id 1
        car = new Car("2", "red", "2000", "supercar");

        target.path(url_pattern).path(path)
                .path(car.getId()).request(MediaType.APPLICATION_XML)
                .put(Entity.entity(car, MediaType.APPLICATION_XML));

        // Get the all todos, id 1 should be created
        System.out.println(target.path("rest").path("cars")
                .request(MediaType.APPLICATION_XML).get(String.class));
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri(
                "http://localhost:8080/BetterBe6_war").build();
    }

}
