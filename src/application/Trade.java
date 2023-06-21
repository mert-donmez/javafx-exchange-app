package application;

public class Trade {
    private int tradeId;
    private double price;
    private double btcAmount;
    private double usdAmount;
    private String dateClosed;
    private String action;

    public Trade(int tradeId, String action,double price, double btcAmount, double usdAmount, String dateClosed) {
        this.tradeId = tradeId;
        this.action=action;
        this.price = price;
        this.btcAmount = btcAmount;
        this.usdAmount = usdAmount;
        this.dateClosed = dateClosed;
        
    }

    public int getTradeId() {
        return tradeId;
    }
    public String getAction() {
        return action;
    }
    public double getPrice() {
        return price;
    }

    public double getBtcAmount() {
        return btcAmount;
    }

    public double getUsdAmount() {
        return usdAmount;
    }

    public String getDateClosed() {
        return dateClosed;
    }
}
