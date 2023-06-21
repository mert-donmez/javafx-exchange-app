package application;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.CoinbyDatabase.util.DatabaseUtil;

public class UserDBO {
    private Connection conn;
    private PreparedStatement sqlQuery;
    private ResultSet resultSet;

    public UserDBO() {
        conn = DatabaseUtil.connect();
    }

    public boolean isUserExist(String username) {
        String sql = "SELECT * FROM user_infos WHERE user_name = ?";
        try {
            sqlQuery = conn.prepareStatement(sql);
            sqlQuery.setString(1, username);
            resultSet = sqlQuery.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println(e.toString());
            return true;
        }
    }

    public void insertUser(String username, String password, Double usdAmount, Double btcAmount) {
        String sql = "INSERT INTO user_infos (user_name, password, usd, btc) VALUES (?, ?, ?, ?)";
        try {
            sqlQuery = conn.prepareStatement(sql);
            sqlQuery.setString(1, username);
            sqlQuery.setString(2, password);
            sqlQuery.setDouble(3, usdAmount);
            sqlQuery.setDouble(4, btcAmount);
            sqlQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public boolean isUserValid(String username, String password) {
        String sql = "SELECT * FROM user_infos WHERE user_name = ? AND password = ?";
        try {
            sqlQuery = conn.prepareStatement(sql);
            sqlQuery.setString(1, username);
            sqlQuery.setString(2, password);
            resultSet = sqlQuery.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public ResultSet getWalletDetails(String username) {
        String sql = "SELECT usd, btc FROM user_infos WHERE user_name=?";
        try {
            sqlQuery = conn.prepareStatement(sql);
            sqlQuery.setString(1, username);
            resultSet = sqlQuery.executeQuery();
            return resultSet;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
    public void buyBTC(int user_id, double btc_amount, double usdt_amount,double price) {
        String sql = "INSERT INTO trades (user_id, buy_or_sell,price, btc_amount, usdt_amount, date_closed) VALUES (?, ?,?, ?, ?, NOW())";
        try {
            sqlQuery = conn.prepareStatement(sql);
            sqlQuery.setInt(1, user_id);
            sqlQuery.setString(2, "BUY");
            sqlQuery.setDouble(3, price);
            sqlQuery.setDouble(4, btc_amount);
            sqlQuery.setDouble(5, usdt_amount);
            
            sqlQuery.executeUpdate();
            
            updateWalletAfterBuy(user_id, btc_amount, usdt_amount);
            System.out.println("Buy order executed successfully.");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void sellBTC(int user_id, double btc_amount, double usdt_amount,double price) {
        String sql = "INSERT INTO trades (user_id, buy_or_sell,price,btc_amount, usdt_amount, date_closed) VALUES (?, ?, ?,?, ?, NOW())";
        try {
            sqlQuery = conn.prepareStatement(sql);
            sqlQuery.setInt(1, user_id);
            sqlQuery.setString(2, "SELL");
            sqlQuery.setDouble(3, price);
            sqlQuery.setDouble(4, btc_amount);
            sqlQuery.setDouble(5, usdt_amount);
            sqlQuery.executeUpdate();
            updateWalletAfterSell(user_id, btc_amount, usdt_amount);
            System.out.println("Sell order executed successfully.");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void updateWalletAfterBuy(int user_id, double btc_amount, double usdt_amount) {
        try {
            String sql = "UPDATE user_infos SET btc = btc + ?, usd = usd - ? WHERE id = ?";
            sqlQuery = conn.prepareStatement(sql);
            sqlQuery.setDouble(1, btc_amount);
            sqlQuery.setDouble(2, usdt_amount);
            sqlQuery.setInt(3, user_id);
            sqlQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString()+"3");
        }
    }

    public void updateWalletAfterSell(int user_id, double btc_amount, double usdt_amount) {
        try {
            String sql = "UPDATE user_infos SET btc = btc - ?, usd = usd + ? WHERE id = ?";
            sqlQuery = conn.prepareStatement(sql);
            sqlQuery.setDouble(1, btc_amount);
            sqlQuery.setDouble(2, usdt_amount);
            sqlQuery.setInt(3, user_id);
            sqlQuery.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString()+"2");
        }
    }
    public int getUserIdByUsername(String username) {
        String sql = "SELECT id FROM user_infos WHERE user_name = ?";
        try {
            sqlQuery = conn.prepareStatement(sql);
            sqlQuery.setString(1, username);
            resultSet = sqlQuery.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                return -1; 
            }
        } catch (SQLException e) {
            System.out.println(e.toString()+"1");
            return -1;
        }
    }
    
    public List<Trade> getTrades(String username) {
        String sql = "SELECT * FROM trades WHERE user_id=?";
       

        try {
            int userId = getUserIdByUsername(username);  
            if (userId == -1) {
                System.out.println("User not found.");
                
                return null;
            }

            sqlQuery = conn.prepareStatement(sql);
            sqlQuery.setInt(1, userId);
            resultSet = sqlQuery.executeQuery();

            List<Trade> tradeList = new ArrayList<>();
            while (resultSet.next()) {
                int tradeId = resultSet.getInt("id");
                double price = resultSet.getDouble("price");
                String action = resultSet.getString("buy_or_sell");
                double btcAmount = resultSet.getDouble("btc_amount");
                double usdAmount = resultSet.getDouble("usdt_amount");
                String dateClosed = resultSet.getString("date_closed");

                Trade trade = new Trade(tradeId,action,price, btcAmount, usdAmount, dateClosed);
                tradeList.add(trade);
            }

            return tradeList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



}
