package com.client;

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
        if(buffRead.readLine().length()>1){
                        Platform.runLater(new Alert(Alert.AlertType.INFORMATION, "A transaction has been completed").show());            
        }
          
      }
   }
 }