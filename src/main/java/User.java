package main.java;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    /**
     * The first name of the user.
     */
    private String firstName;
    /**
     * The last name of the user.
     */
    private String lastName;
    /**
     * The ID number of the user.
     */
    private String uuid;
    /**
     * The MD5 hash of the user's pin number.
     */
    private byte pinHash[];
    /**
     * The list of accounts for this user.
     */
    private ArrayList<Account> accounts;

    /**
     * Create a new user
     * @param firstName the user's firstn ame
     * @param lastName the user's last name
     * @param pin the user's account pin number
     * @param theBank the Bank object that the yser us a cystiner of
     */
    public User(String firstName, String lastName, String pin, Bank theBank) {

        // set user's first name
        this.firstName = firstName;
        this.lastName = lastName;

        // store the pin's MD5 hash, rather than original value
        // for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        // get a new, unique universal ID for the user
        this.uuid = theBank.getNewUserUUID();

        // create empty list of accounts
        this.accounts = new ArrayList<Account>();

        // print log message
        System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);

    }

    /**
     * Add an account for the user
     * @param anAcct the Account to add
     */
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }

    public String getUuid(){
        return uuid;
    }

    /**
     * Check whether a given pin matches the true User pin
     * @param aPin the pin to check
     * @return wheter the pin is valid or not
     */
    public boolean validatePin(String aPin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), 
            this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    /**
     * Return the user's first name
     * @return the first name
     */
    public String getFirstName(){
        return this.firstName;
    }

    /**
     * Print summaries for the accounts of this user
     */
    public void printAccountsSummary(){
        System.out.printf("\n\n%s's accounts summary:\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++){
            System.out.printf(" %d) %s\n", a+1,this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    /**
     *  Get the number of accounts of the user
     * @return the number of accounts
     */
    public int numAccounts(){
        return this.accounts.size();
    }

    /**
     * Print transaction histoy for a particular account
     * @param acctIdx the index of the account to use
     */
    public void printAcctTransHistory(int acctIdx){

        this.accounts.get(acctIdx).printTransHistory();

    }

    /**
     * Get the balance of a particular account
     * @param acctIdx the index of the account to user
     * @return the balance of the account
     */
    public double getAcctBalance(int acctIdx){
        return this.accounts.get(acctIdx).getBalance();
    }

    /**
     * Get the UUID of a particular account
     * @param acctIdx the index of the account to use
     * @return the UUID of the account
     */
    public String getAccountUUID(int acctIdx){ 
        return this.accounts.get(acctIdx).getUuid();
    }

    /**
     * Add a transaction to a particular account
     * @param acctIdx the index of the account
     * @param amount the amount of the transaction
     * @param memo the memo of the transaction
     */
    public void addAcctTransaction(int acctIdx, double amount, String memo){
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }


}