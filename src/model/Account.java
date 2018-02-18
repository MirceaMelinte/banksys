package model;

import java.io.Serializable;

public class Account implements Serializable
{
   private static final long serialVersionUID = 1L;
   private int accNo;
   private String customerName;
   private double balance;
   
   public Account(int accNo, String customerName, double balance)
   {
      this.accNo = accNo;
      this.customerName = customerName;
      this.balance = balance;
   }
   
   public Account(int accNo, String customerName)
   {
      this.accNo = accNo;
      this.customerName = customerName;
   }
   
   public Account(String customerName, double balance)
   {
      this.customerName = customerName;
      this.balance = balance;
   }
   
   public int getAccNo()
   {
      return accNo;
   }
   
   public String getCustName()
   {
      return customerName;
   }
   
   public double getBalance()
   {
      return balance;
   }
   
   public void setBalance(double balance)
   {
      this.balance = balance;
   }
}
