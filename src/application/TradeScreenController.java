package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class TradeScreenController {

    private double currentPrice;
    private final double initialPrice = 29850.00;
    private final double maxFluctuationPercentage = 0.001;
    private final double minFluctuationPercentage = -0.001;
    TradeMotor tradeMotor;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_buy_btc;

    @FXML
    private Button button_sell_btc;

    @FXML
    private Button button_refresh;

    @FXML
    private Label label_btc_in_wallet;

    @FXML
    private Label label_btc_price;

    @FXML
    private Label label_usd_in_wallet;

    @FXML
    private TextField textinput_amount_usd;

    @FXML
    private TextField textinput_amount_btc;

    @FXML
    private TableColumn<?, ?> col_action;

    @FXML
    private TableColumn<?, ?> col_btc_amount;

    @FXML
    private TableColumn<?, ?> col_date;

    @FXML
    private TableColumn<?, ?> col_price;

    @FXML
    private TableColumn<?, ?> col_tradeid;

    @FXML
    private TableColumn<?, ?> col_usd_amount;
    
    @FXML
    private TableView<Trade> table_tradehistory;



    @FXML
    private TextField textinput_limit_price;

    @FXML
    private Label label_total_value;



    @FXML
    void button_buy_btc_clicked(ActionEvent event) {
        String username = UserSession.getUsername();
        double usdAmount = Double.parseDouble(textinput_amount_usd.getText());
        double btcAmount = usdAmount / currentPrice;
        tradeMotor.buyBTC(username, btcAmount, usdAmount, currentPrice);
    }

    @FXML
    void button_sell_btc_clicked(ActionEvent event) {
        String username = UserSession.getUsername();
        double btcAmount = Double.parseDouble(textinput_amount_btc.getText());
        double usdAmount = btcAmount * currentPrice;
        tradeMotor.sellBTC(username, btcAmount, usdAmount, currentPrice);
    }

    @FXML
    void button_refresh_clicked(ActionEvent event) {
        String username = UserSession.getUsername();
        TradeScreenData wallet = new TradeScreenData(username);
        Double usdValue = wallet.getUsdValue();
        Double btcValue = wallet.getBtcValue();
        label_usd_in_wallet.setText(usdValue.toString());
        label_btc_in_wallet.setText(btcValue.toString());
        Double totalValue = (btcValue * currentPrice) + usdValue;
        label_total_value.setText("$" + String.format("%.2f", totalValue));
        

        col_tradeid.setCellValueFactory(new PropertyValueFactory<>("tradeId"));
        col_action.setCellValueFactory(new PropertyValueFactory<>("action"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_btc_amount.setCellValueFactory(new PropertyValueFactory<>("btcAmount"));
        col_usd_amount.setCellValueFactory(new PropertyValueFactory<>("usdAmount"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("dateClosed"));

   
        ArrayList<Trade> tradesList = (ArrayList<Trade>) new UserDBO().getTrades(username);
        ObservableList<Trade> trades = FXCollections.observableArrayList(tradesList);

        table_tradehistory.setItems(trades);

        
    }


    private void updateBTCPrice(ActionEvent event) {
        double fluctuation = generateFluctuation();
        currentPrice += currentPrice * fluctuation;
        label_btc_price.setText("$" + String.format("%.2f", currentPrice));
    }

    private double generateFluctuation() {
        Random random = new Random();
        return minFluctuationPercentage + (maxFluctuationPercentage - minFluctuationPercentage) * random.nextDouble();
    }

    public static void setMainApp(Main mainApp) {
        Main.mainApp = mainApp;
    }

    @FXML
    void initialize() {
        currentPrice = initialPrice;

        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), this::updateBTCPrice));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        Timeline refresh = new Timeline(new KeyFrame(Duration.seconds(1), this::button_refresh_clicked));
        refresh.setCycleCount(Timeline.INDEFINITE);
        refresh.play();

        tradeMotor = new TradeMotor();
        
        

    }
}
