package application;

public class LoginAndRegister {
	private String user_name;
	private String password;
	private Double initialUsdAmount = 100000.00;
	private Double initialBtcAmount = 0.00;
	private UserDBO userDBO;
	private Main mainApp;


	LoginAndRegister(String user_name, String password,Main mainApp) {
		this.user_name = user_name;
		this.password = password;
		this.mainApp = mainApp;
		userDBO = new UserDBO();
	}

	public void registerUser() {
		if (!userDBO.isUserExist(this.user_name)) {
			System.out.println("user not exist. Registering...");
			userDBO.insertUser(this.user_name, this.password, initialUsdAmount, initialBtcAmount);
		} else {
			System.out.println("user already exist");
		}
	}

	public void loginUser() {
	    if (userDBO.isUserValid(this.user_name, this.password)) {
	        System.out.println("Welcome, " + this.user_name);
	        Main mainApp = new Main();
	        mainApp.showTradeScreen();
	    } else {
	        System.out.println("Username or password is incorrect.");
	    }
	}

}
