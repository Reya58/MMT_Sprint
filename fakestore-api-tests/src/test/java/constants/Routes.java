package constants;

public class Routes {

	// ----PRODUCTS-------

	public final static String PRODUCTS = "/products";
	public final static String ADD_A_NEW_PRODUCT = "/products";
	public final static String GET_SINGLE_PRODUCT = "/products/{id}";
	public final static String UPDATE_A_PRODUCT = "/products/{id}";
	public final static String DELETE_A_PRODUCT = "/products/{id}";
	
	
//	-----------CARTS---------------
	public final static String GET_ALL_CARTS = "/carts";
	public final static String ADD_A_NEW_CART = "/carts";
	public final static String GET_A_SINGLE_CART = "/carts/{id}";
	public final static String UPDATE_A_CART = "/carts/{id}";
	public final static String DELETE_A_CART = "/carts/{id}";
	
	
//	------------------USERS----------------
	public final static String GET_ALL_USERs = "/users";
	public final static String ADD_A_NEW_USER = "/users";
	public final static String GET_A_SINGLE_USER = "/users/{id}";
	public final static String UPDATE_A_USER = "/users/{id}";
	public final static String DELETE_A_USER = "/users/{id}";
	
	
//	----------LOGI--------
	public final static String LOGIN = "/auth/login";
	
}
