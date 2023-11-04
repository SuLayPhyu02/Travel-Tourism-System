package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.*;

public class Checker {
	public static boolean isValidName(String n)
	{
		String reg="^[A-Z][a-z]*( [A-Z][a-z]+)*$";
		return Pattern.matches(reg,n);
	}
	public static String digestMsg(String str)
	{
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte arr[]=md.digest();
			return new String(arr);
			
		} catch (NoSuchAlgorithmException e) 
		{
			return "unscuess";
		}
	}
	public static boolean isValidEmail(String e)
	{
		String reg="^[a-z](?=.*\\d)(?=.*[a-z]).{5,15}@(gmail)(\\.)(com)$";
		return Pattern.matches(reg, e);
	}
	public static boolean isValidPassword(String s)
	{
		String reg="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
		return Pattern.matches(reg,s);
	}
	public static void main(String args[])
	{
		System.out.println(isValidEmail("salapha202@gmail.com"));
	}
	

}
