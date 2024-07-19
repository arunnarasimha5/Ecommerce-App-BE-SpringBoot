# E-commerce App BackEnd (Spring Boot)

This project is the backend of a simple E-commerce App that allows users to sign up, sign in, view products, add products to the cart, and make purchases.

The frontend of this project, built with React TypeScript, is available [here](https://github.com/arunnarasimha5/Ecommerce-App-FE-React).

## Getting Started

### Prerequisites

Ensure you have the following installed on your local machine:
- [Spring Tool Suite (STS)](https://spring.io/tools)
- [Java Development Kit (JDK) 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/install.html)

### Backend Setup

1. Clone the Backend Spring Boot repository:
   ```sh
   git clone https://github.com/arunnarasimha5/Ecommerce-App-BE-SpringBoot.git
2. Open the project in STS.
3. Build the project using Maven:
   ```sh
   mvn clean install
4. Run the Spring Boot application on localhost:8080
   * If you need to run it on a different port, update the WebConfig file in the src/main/java/com/example/ecommerce/config/WebConfig.java directory accordingly.

### Frontend Setup

Refer to the Frontend README [here](https://github.com/arunnarasimha5/Ecommerce-App-FE-React) for setting up the frontend.

## *Now, your E-commerce BE App is up and running!*

### Packages Used
* Spring Boot Starter Data JPA - For ORM and database interaction
* H2 Database - For in-memory database
* Spring Boot Starter Web - For building web applications

### Initial Product Data
* The initial product data is loaded from a JSON file located at src/main/resources/productDetails.json.


### Usage Instructions
1. Sign Up: Use the /signup API to create a new user account.
2. Sign In: Use the /login API to log in with your credentials.
3. View Products: Use the /getproductdata/{category} API to retrieve products.
4. Add to Cart: Use the /addtocart API to add products to the cart.
5. Delete from Cart: Use the /deletefromcart API to remove products from the cart.
6. Purchase: Use the /buycartproducts API to complete the purchase of the items in the cart.
7. Retrieve User Data: Use the /getcurrentuserdata API to get the current user data.
8. Update User Data: Use the /updateuserdata API to update user information.
