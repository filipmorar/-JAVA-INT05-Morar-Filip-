import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;

public class AccountManager {

    //return the next maximum first interest rate available
    public double getNextMaxInterestRate1(List<Account> accountList,double maxRate){
        double newMaxRate=0;
        for(Account a : accountList){
            if(newMaxRate < a.getRate1() && a.getRate1()<maxRate) {
                newMaxRate=a.getRate1();
            }
        }
        return newMaxRate;
    }

    //return the maximum second interest rate available
    public double getNextMaxInterestRate2(List<Account> accountList,double maxRate){
        double newMaxRate=0;
        for(Account a : accountList){
            if(newMaxRate<a.getRate2() && a.getRate2()<maxRate){
                newMaxRate=a.getRate2();
            }
        }
        return newMaxRate;
    }

    //return the total amount of money from all accounts
    public double getTotalAmount(List<Account> accountList){
        double totalAmount=0;
        for(Account a:accountList){
            totalAmount+=a.getAvailableAmount();
        }
        return totalAmount;
    }

    //find an account by it's first rate
    public Account findByRate1(List<Account> accountList,double rate) {
        for (Account a : accountList) {
            if (a.getRate1() == rate) {
                return a;
            }
        }
        return null;
    }

    //find an account by it's second rate
    public Account findByRate2(List<Account> accountList,double rate) {
        for (Account a : accountList) {
            if (a.getRate2() == rate) {
                return a;
            }
        }
        return null;
    }


    //redistribute the money in order to get the maximum interest rate, based on the rate percentage and available amount on each account
    public List<Account> redistributeMoney(List<Account> accountList){
        double maxRate1 = getNextMaxInterestRate1(accountList,100);
        double maxRate2 = getNextMaxInterestRate2(accountList,100);
        double maxRate = maxRate1;//overall maximum rate initially equal to first (first rate will always be bigger than the second rate)
        double totalAmount = getTotalAmount(accountList);
        Account auxAccount; // auxiliary account needed for setting

        //set all available amounts to 0 initially
        for(Account a:accountList){
            a.setAvailableAmount(0);
        }

        while(maxRate!=0 && totalAmount!=0){

            //if the overall maximum rate is equal to the maximum rate of the first rates
            if(maxRate == maxRate1){

                auxAccount = findByRate1(accountList,maxRate);//get the account from the list
                if(totalAmount >= auxAccount.getIntervalLimit1()){// if the total amount is bigger than the first interval limit

                    auxAccount.setAvailableAmount(auxAccount.getAvailableAmount() + auxAccount.getIntervalLimit1());//add the limit to the available amount
                    totalAmount -= auxAccount.getIntervalLimit1();// decrease the total amount

                }
                else{

                    auxAccount.setAvailableAmount(auxAccount.getAvailableAmount() + totalAmount);// add what's left in the account
                    totalAmount = 0;// and empty the total amount

                }

                accountList.set(accountList.indexOf(auxAccount),auxAccount);// set the account accordingly
                maxRate1=getNextMaxInterestRate1(accountList,maxRate1);// get a new max interest rate smaller than the one before

            }
            else{

                auxAccount=findByRate2(accountList,maxRate);//find the account with the second rate equal to the overall max rate

                if(totalAmount >= auxAccount.getIntervalLimit2()){//if the total amount is bigger than the second interval limit

                    auxAccount.setAvailableAmount(auxAccount.getAvailableAmount() + (auxAccount.getIntervalLimit2()-auxAccount.getIntervalLimit1()));// add the difference of limits to the total amount
                    totalAmount = totalAmount - (auxAccount.getIntervalLimit2() - auxAccount.getIntervalLimit1());// decrease the total amount accordingly

                }
                else{

                    auxAccount.setAvailableAmount(auxAccount.getAvailableAmount() + totalAmount);// add what's left
                    totalAmount = 0;

                }
                accountList.set(accountList.indexOf(auxAccount),auxAccount);//set the account accordingly
                maxRate2 = getNextMaxInterestRate2(accountList,maxRate2);//get a new second max interest rate
            }

            maxRate = Math.max(maxRate1,maxRate2);//get a new overall max rate

        }

        return accountList;

    }
    //calculate number of months between current date(2019-03-19) and the accountExpirationDate
    public long noOfMonthsBetween(Calendar accountExpirationDate){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String strdate = sdf.format(accountExpirationDate.getTime());

        return ChronoUnit.MONTHS.between(LocalDate.parse("2019-03-19").withDayOfMonth(1), LocalDate.parse(strdate).withDayOfMonth(1));

    }
    public double calculateTotalAmountAfter39Months(List<Account> accountList){

        long depositDuration = 0; // number of months between current date and the account expiration date
        double interest = 0; // interest of an account
        double totalInterest = 0;
        Calendar accountExpirationDate = Calendar.getInstance();
        for(Account a: accountList){

            if(a.getAvailableAmount()!=0){//if there is money in an account

                accountExpirationDate.setTime(a.getExpirationDate());//set the expiration date
                depositDuration = noOfMonthsBetween(accountExpirationDate);// set the number of months

                if(a.getAvailableAmount()<=a.getIntervalLimit1()){//if the total amount is smaller than the first interval limit
                    interest = a.getAvailableAmount()*a.getRate1()*((double)depositDuration/12);//set the interest (according to Banca Transilvania: Interest = Amount x Interest Rate (%) x Deposit Duration (no of months)/12
                }

                else{
                    interest = a.getAvailableAmount()*a.getRate1()*((double)depositDuration/12) + (a.getAvailableAmount()-a.getIntervalLimit1())*a.getRate2()*((double)depositDuration/12);// set the interest adding the one for the first limit to the one to the second limit
                }
                totalInterest += interest;// increase the total interest
            }
        }
        return getTotalAmount(accountList)+totalInterest;// add the interest to the total amount
    }
}