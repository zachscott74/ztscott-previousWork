import java.awt.EventQueue;

import javax.swing.*;        

public class DriverApp {

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
   
    	SerialTest serialConn = new SerialTest();
		try {
			serialConn.initialize();
		}	
		catch (Exception e) {};
		
		System.out.println("Going to sleep");
		
		try {
			/*sleep because when port opened, arduino resets*/
			Thread.sleep(4000);
		}
		catch (Exception e) {};
		
        
		EventQueue.invokeLater(() -> {
        	MainGridFrame gridFrame = new MainGridFrame(serialConn);
            gridFrame.setVisible(true);
        });

    }
}

////WRITING TO SERIAL PORT
//while (true) {
//	//pause for now (2 seconds)
//	Thread.sleep(2000);
//	writeData('a');
//}