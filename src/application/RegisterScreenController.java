package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.CoinbyDatabase.util.DatabaseUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.*;


public class RegisterScreenController {
	
	

	public RegisterScreenController() {
		conn = DatabaseUtil.connect();
	}
	
	
	

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_login;

    @FXML
    private Button button_register;

    @FXML
    private TextField textinput_login_email;

    @FXML
    private TextField textinput_login_password;

    @FXML
    private TextField textinput_register_email;

    @FXML
    private TextField textinput_register_password;

    @FXML
    private TextField textinput_register_password_again;
    
    Connection conn = null;
	PreparedStatement sqlQuery = null;
	ResultSet resultSet = null;
	String sql;
	

    @FXML
    void button_login_clicked(ActionEvent event) {
    	String username=textinput_login_email.getText().trim();
    	String password=textinput_login_password.getText().trim();
    	try {
    		LoginAndRegister login = new LoginAndRegister(username, password, Main.mainApp);
    		login.loginUser();
    		UserSession.setUsername(username);
    		
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    }

    @FXML
    void button_register_clicked(ActionEvent event) {
    	String username = textinput_register_email.getText().trim();
    	String password = textinput_register_password.getText().trim();
    	String password_againString = textinput_register_password_again.getText().trim();
    	
    	try {
			if(password.equals(password_againString) && !username.isEmpty() && !password.isEmpty()) {
				LoginAndRegister register = new LoginAndRegister(username, password, Main.mainApp);

		    	register.registerUser();
			}
			else {
				System.out.println("passwords not matched");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    	
    	

    }
    
    public static void setMainApp(Main mainApp) {
        Main.mainApp = mainApp;
    }
    


    @FXML
    void initialize() {
        

    }
    

	

	

}
