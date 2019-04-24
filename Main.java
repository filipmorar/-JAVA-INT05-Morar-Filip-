import java.time.Period;
import java.util.*;

import static java.util.Calendar.MAY;

public class Main {
    public static void main(String[] args) {
        List<Account> listAcc = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, MAY);
        cal.set(Calendar.DAY_OF_MONTH, 23);
        Date date = cal.getTime();
        listAcc.add(new Account("Silver",0.003,0.002,500,5000,date,5000));
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.JULY);
        cal.set(Calendar.DAY_OF_MONTH, 5);
        date = cal.getTime();
        listAcc.add(new Account("Gold",0.006,0.004,500,5000,date,700));
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 15);
        date = cal.getTime();
        listAcc.add(new Account("Platinum",0.009,0.005,500,5000,date,300));

        AccountManager redist = new AccountManager();

        listAcc = redist.redistributeMoney(listAcc);

        for(Account a:listAcc){
            System.out.println("Account no.: " + listAcc.indexOf(a));
            System.out.println("Account type: " + a.getType());
            System.out.println("First interest rate: " + a.getRate1());
            System.out.println("Second interest rate: " + a.getRate2());
            System.out.println("First interval upper limit: " + a.getIntervalLimit1());
            System.out.println("Second interval upper limit: " + a.getIntervalLimit2());
            System.out.println("Account expiration date: " + a.getExpirationDate());
            System.out.println("Available amount: " + a.getAvailableAmount());
            System.out.println();
        }

        System.out.println("Total amount after 39 months: "+ redist.calculateTotalAmountAfter39Months(listAcc));
    }


}