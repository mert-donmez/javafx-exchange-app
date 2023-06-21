package application;



public class TradeMotor {
    private UserDBO userDBO;
    

    public TradeMotor() {
        userDBO = new UserDBO();
    }

    public void buyBTC(String username, double btc_amount, double usd_amount,double price) {
        if (!validateUsdAmount(usd_amount)) {
            System.out.println("Invalid amount.");
            return;
        }

        int user_id = userDBO.getUserIdByUsername(username);
        if (user_id == -1) {
            System.out.println("User not found.");
            return;
        }

        userDBO.buyBTC(user_id, btc_amount, usd_amount,price);
        System.out.println("Buy order executed successfully.");
    }

    public void sellBTC(String username, double btc_amount, double usd_amount,double price) {
        if (!validateBtcAmount(btc_amount)) {
            System.out.println("Invalid amount.");
            return;
        }

        int user_id = userDBO.getUserIdByUsername(username);
        if (user_id == -1) {
            System.out.println("User not found.");
            return;
        }

        userDBO.sellBTC(user_id, btc_amount, usd_amount,price);
        System.out.println("Sell order executed successfully.");
    }

    private boolean validateUsdAmount(double usd_amount) {
        String username = UserSession.getUsername();
        TradeScreenData wallet = new TradeScreenData(username);
        double usdValue = wallet.getUsdValue();
        
        
        if (usd_amount <= usdValue ) {
            return true;
        } else {
            return false;
        }
    }
    private boolean validateBtcAmount(double btc_amount) {
        String username = UserSession.getUsername();
        TradeScreenData wallet = new TradeScreenData(username);
        
        double btcValue = wallet.getBtcValue();
        
        if (btc_amount <= btcValue) {
            return true;
        } else {
            return false;
        }
    }


}
