package application;

import java.sql.ResultSet;

public class TradeScreenData {
		
	private String username;
	private ResultSet resultSet;
	
	public TradeScreenData(String username) {
		this.username=username;
		
	}
	
	public double getUsdValue() {
		try {
			UserDBO userDBO = new UserDBO();
			ResultSet walletResultSet;
			walletResultSet = userDBO.getWalletDetails(username);
			if(walletResultSet.next()) {
				 double usdAmount = walletResultSet.getDouble("usd");
				 return usdAmount;
			}
		} 
		catch (Exception e) {
			System.out.println(e.toString());
			return 0;
		}
		return 0;
	}
	public double getBtcValue() {
		try {
			UserDBO userDBO = new UserDBO();
			ResultSet walletResultSet;
			walletResultSet = userDBO.getWalletDetails(username);
			if(walletResultSet.next()) {
				 double usdAmount = walletResultSet.getDouble("btc");
				 return usdAmount;
			}
		} 
		catch (Exception e) {
			System.out.println(e.toString());
			return 0;
		}
		return 0;
	}

}
