import java.io.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class SKencrypt implements Serializable{
 public static void main(String args[]){
  	try{
	FileInputStream inFile = new FileInputStream(args[0]);
	FileInputStream keyFile = new FileInputStream(args[1]);
	ObjectInputStream skeyFile = new ObjectInputStream(keyFile);
	SecretKey skey = (SecretKey) skeyFile.readObject();
	String algorithm = skey.getAlgorithm();
	byte[] buf = new byte[8];
	byte[] gbuf;
	int len;
	keyFile.close();
	Cipher cipher = Cipher.getInstance(algorithm+"/"+args[3]+"/PKCS5Padding");
	cipher.init(Cipher.ENCRYPT_MODE,skey);
        FileOutputStream encrypted = new FileOutputStream(args[2]);
        FileOutputStream ivFile = new FileOutputStream(args[4]);
	while(( len = inFile.read(buf)) != -1){
		gbuf = cipher.update(buf,0,len);
		encrypted.write(gbuf);
	}
	gbuf = cipher.doFinal();
	if(gbuf != null){
		encrypted.write(gbuf);
	}
	byte[] iv = cipher.getIV();
	ivFile.write(iv);
	ivFile.close();
	inFile.close();
	encrypted.close();
	}
	catch (Exception e){
		System.err.println(e.getMessage());
		}
 }
}
