public class Main {
    public static void main(String[] args) {
        try {
            Account account = new Account(10000);
            debit(account, -1000); //InvalidAmountException
            debit(account, 13000); //InvalidBalanceException
            debit(account, 1000);
            credit(account, -1000);//InvalidAmountException
            credit(account, 2000);
        } catch (InvalidBalanceException e) {
            e.printStackTrace();
        }
        Account account = null;
        try {
            account = new Account(-10000); //InvalidBalanceException
        } catch (InvalidBalanceException e) {
            e.printStackTrace();
        }
        debit(account, -10000); //NullPointerException
        debit(account, 10000);
        credit(account, -10000);
        credit(account, 10000);
    }
    public static void credit(Account account, double amount) {
        try {
            account.credit(amount);
        } catch (InvalidAmountException e) {
            e.printStackTrace();
        }
    }
    public static void debit(Account account, double amount){
        try{
            account.debit(amount);
        } catch (InvalidAmountException e) {
            e.printStackTrace();
        } catch (InvalidBalanceException e) {
            e.printStackTrace();
        }
    }
}