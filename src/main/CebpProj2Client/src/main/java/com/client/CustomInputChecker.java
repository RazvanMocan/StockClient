package main.java.com.client;

import java.io.BufferedReader;
import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class CustomInputChecker implements Runnable{

private static BufferedReader buffRead;

public CustomInputChecker(BufferedReader buffRdr){
  this.buffRead = buffRdr;
  }
  @Override
    public void run() {
      String value;
      while(true)
      {
        try {
			if(buffRead.readLine().startsWith("Transaction finished")){          
			                Platform.runLater(new Runnable() {public void run() {new Alert(Alert.AlertType.INFORMATION, "A transaction has been completed").show();}});
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
      }
   }
 }
