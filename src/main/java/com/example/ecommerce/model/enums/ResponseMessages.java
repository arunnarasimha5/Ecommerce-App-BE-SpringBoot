package com.example.ecommerce.model.enums;

public enum ResponseMessages {
	LOGINERROR("Invalid UserName/Password"), LOGINFAILED("Login Failed. Pls Try Again"),
	SIGNUPFAILED("Sign Up Failed, Pls Try Again"), SIGNUPUSEREXISTS("Same UserID already exists"),
	SIGNUPSUCCESS("Sign Up Success, Please Sign In"), NOPRODUCTFOUND("No Product Details Found"),
	LOGOUTSUCCESS("User Logout Successfully "), ADDCARTNOCREDIT("Not Enough Credit Available"),
	ADDCARTPRESENTALREADY("The Product is already added in the cart"),
	ADDTOCARTSUCCESS("Product has been successfully added to the Cart"),
	ADDTOCARTFAILED("Adding Product to cart has been failed. Please Try Again"),
	DELETECARTFAILED("Deleting Product from cart Failed"), DELETECARTSUCCESS("Delete Product from cart Success"),
	USERDATAFETCHFAILED("User Data Fetch Failed"), USERUPDATESUCCESS("User Data Updated Successfully"),
	USERUPDATEPASSMISMATCH("Old Password is wrong !! Please Try Again"), USERUPDATEFAILED("User Update Failed"),
	PRODUCTBUYSUCCESS("Product has beeen successfully purchased !!");

	private final String responseMessage;

	private ResponseMessages(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponsesMessage() {
		return responseMessage;
	}

}
