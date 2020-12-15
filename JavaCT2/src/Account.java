public class Account {
    private double balance;
    public Account(double balance) throws InvalidBalanceException {
        if(balance < 0){
            throw new InvalidBalanceException();
        }
        this.balance = balance;
    }
    public void debit(double amount) throws InvalidAmountException, InvalidBalanceException {
        if(amount < 0) throw new InvalidAmountException();
        if(balance - amount < 0) throw new InvalidBalanceException();

        balance-=amount;
        System.out.println("Successful Debit of amount: " + amount);
    }
    public void credit(double amount) throws InvalidAmountException{
        if(amount < 0) throw new InvalidAmountException();

        balance+=amount;
        System.out.println("Successful Credit of amount: "+ amount);
    }
}
class InvalidAmountException extends Exception{
    public InvalidAmountException() {
        super("The given amount can't be negative.");
    }
}
class InvalidBalanceException extends Exception{
    public InvalidBalanceException() {
        super("The account balance can't be less than zero.");
    }
}