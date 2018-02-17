package model;

import java.io.Serializable;

public class Transaction implements Serializable
{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private String type;
   private Account account;
   private double amount;

   public Transaction(String type, Account account, double amount)
   {
      this.type = type;
      this.account = account;
      this.amount = amount;
   }
   
   public String getType()
   {
      return type;
   }
   
   public Account getAccount()
   {
      return account;
   }
   
   public double getAmount()
   {
      return amount;
   }
}
