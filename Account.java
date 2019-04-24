import java.util.Date;

public class Account{
    private String type;
    private double rate1;
    private double rate2;
    private double intervalLimit1;
    private double intervalLimit2;
    private Date expirationDate;
    private double availableAmount;

    public Account(String type, double rate1, double rate2, double intervalLimit1,double intervalLimit2, Date expirationDate, double availableAmount) {
        this.type = type;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.intervalLimit1 = intervalLimit1;
        this.intervalLimit2 = intervalLimit2;
        this.expirationDate = expirationDate;
        this.availableAmount = availableAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRate1() {
        return rate1;
    }

    public void setRate1(double rate1) {
        this.rate1 = rate1;
    }

    public double getRate2() {
        return rate2;
    }

    public void setRate2(double rate2) {
        this.rate2 = rate2;
    }

    public double getIntervalLimit1() {
        return intervalLimit1;
    }

    public void setLowerInterval1(double intervalLimit1) {
        this.intervalLimit1 = intervalLimit1;
    }

    public double getIntervalLimit2() {
        return intervalLimit2;
    }

    public void setIntervalLimit2(double intervalLimit2) {
        this.intervalLimit2=intervalLimit2;
    }


    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }
}