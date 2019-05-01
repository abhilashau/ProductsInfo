/*
 * package ProductsPkg;
 * 
 * import java.util.List;
 * 
 * import javax.ws.rs.GET; import javax.ws.rs.Path; import
 * javax.ws.rs.PathParam; import javax.ws.rs.Produces; import
 * javax.ws.rs.core.Context; import javax.ws.rs.core.MediaType; import
 * javax.ws.rs.core.Request; import javax.ws.rs.core.UriInfo;
 * 
 * @Path("/product") public class Produts {
 * 
 * 
 * @GET
 * 
 * @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON) public String
 * sayHello() { String response = null; return response; }
 * 
 * @GET
 * 
 * @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
 * 
 * @Path("{prod}") public String sayHello(@PathParam("prod") String prod) {
 * String response = "<?xml version='1.0'?>" + "<hello>Hello" +prod+"</hello>";
 * return response; }
 * 
 * 
 * @Context UriInfo uriInfo;
 * 
 * @Context Request request;
 * 
 * ProductService productService;
 * 
 * public Produts() { productService = new ProductService(); }
 * 
 * @GET
 * 
 * @Produces({ MediaType.APPLICATION_JSON }) public List<Product> getProducts()
 * { return productService.getProductAsList(); }
 * 
 * }
 */