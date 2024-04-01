package ecomSystemFlow;
import ecomSystemFlow.pojo.LoginRequest;
import ecomSystemFlow.pojo.LoginResponse;
import ecomSystemFlow.pojo.OrderDetails;
import ecomSystemFlow.pojo.OrdersList;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;

public class EcomSystemFlow {
    public static void main(String[] args)
    {
        //Creating the Spec Builder for reusable methods
        //Return type must be RequestSpecification
        RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://www.rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        //***************Login API*****************************
        // Create object of LoginRequest and set all the information
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setUserEmail("vivnay111@gmail.com");
        loginRequest.setUserPassword("Binay@123");

        // Dividing given,when then in multiple lines by using object.
        RequestSpecification reqlogin=given().log().all().spec(req).body(loginRequest);
        LoginResponse loginResponse=reqlogin.when().post("api/ecom/auth/login")
                .then().log().all().statusCode(200).extract()
                .response().as(LoginResponse.class);
        //In above code whatever response it will receive, will store in LoginResponse class's variable
        // We can retrieve that info using get pojo method.
        String tokenID=loginResponse.getToken();
        String userID=loginResponse.getUserId();
        System.out.println("Token ID : "+tokenID);
        System.out.println("UserId : "+userID);


        //*****************Create a Product******************

        //Request Spec Builder for add product
        RequestSpecification addProduct=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",tokenID).build();

        //param is using for form data
        // To upload or attached any file we are using multiPart with key name and file path.
        RequestSpecification reqAddProduct=given().spec(addProduct)
                .param("productName","iPhone")
                .param("productAddedBy",userID)
                .param("productCategory","electronics")
                .param("productSubCategory","mobiles")
                .param("productPrice",789000)
                .param("productDescription","8 GB 256 GB ROM")
                .param("productFor","ALL")
                .multiPart("productImage",new File("C:\\Users\\SC-229-USER\\Downloads\\iphone-15.jpg"));

        String addproductResponse= reqAddProduct.when().log().all().post("api/ecom/product/add-product")
                .then().log().all().statusCode(201).extract().asString();

        //Instead of Pojo class here we are using JSON path to fetch the value of given key.
        JsonPath js=new JsonPath(addproductResponse);
        String placeOrderId=js.get("productId");
        System.out.println("ProductId="+placeOrderId);


        //***********************PlaceAOrder**********************
        RequestSpecification placeAOrder=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",tokenID)
                .setContentType(ContentType.JSON).build();

        //Creating body through pojo classes

        OrderDetails orderdetails=new OrderDetails();
        orderdetails.setCountry("India");
        orderdetails.setProductOrderedId(placeOrderId);

        //OrderList is excepting list data of order so we have to pass in list
        List<OrderDetails> orderDetailsList=new ArrayList<OrderDetails>();
        orderDetailsList.add(orderdetails);

        //Sending this orderDetailsList to ordersList class
        OrdersList orders=new OrdersList();
        orders.setOrders(orderDetailsList);

        //Place a Order

        RequestSpecification placeAOrderReq=given().spec(placeAOrder).body(orders);

        String OrderResponse=placeAOrderReq
                .when().log().all()
                .post("api/ecom/order/create-order")
                .then().log().all()
                .extract().asString();
        System.out.println("Response=  "+OrderResponse);

        JsonPath js2=new JsonPath(OrderResponse);
        String  orderId=js2.get("orders[0]");
        System.out.println("Order Id is "+orderId);

        //**********************View the order details************************
        //using Query parameters
        RequestSpecification viewOrderDetails=given().spec(placeAOrder)
                .queryParam("id",orderId);

        String orderDeatilsResponse=viewOrderDetails.when().log().all()
                .get("api/ecom/order/get-orders-details")
                .then().log().all().header("Content-Type","application/json; charset=utf-8")
                .extract().asString();

        System.out.println("Order Details is \n" + orderDeatilsResponse);


        //*********************************Delete the product********************************
        //Using Path parameters
        RequestSpecification deleteProduct=given().spec(placeAOrder)
                .pathParam("product_Id",placeOrderId);

        String deleteProdouctResponse=deleteProduct.when().log().all()
                .delete("api/ecom/product/delete-product/{product_Id}")
                .then().log().all()
                .extract().asString();

        JsonPath js4=new JsonPath(deleteProdouctResponse);

        Assert.assertEquals("Product Deleted Successfully",js4.get("message"));

        //********************Delete the ordered product************************************
        // using path parameters

        RequestSpecification deleteAOrder=given().spec(placeAOrder).
                pathParam("order_ID",orderId);

        String deleteOrderResponse=deleteAOrder.when().log().all()
                .delete("api/ecom/order/delete-order/{order_ID}")
                .then().log().all().assertThat().statusCode(200).extract().asString();

        JsonPath js5=new JsonPath(deleteOrderResponse);

        Assert.assertEquals("Orders Deleted Successfully",js5.get("message"));


    }

}
