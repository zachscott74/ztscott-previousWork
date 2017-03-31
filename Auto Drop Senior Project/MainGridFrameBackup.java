/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/**
 * This example, like all Swing examples, exists in a package:
 * in this case, the "start" package.
 * If you are using an IDE, such as NetBeans, this should work 
 * seamlessly.  If you are compiling and running the examples
 * from the command-line, this may be confusing if you aren't
 * used to using named packages.  In most cases,
 * the quick and dirty solution is to delete or comment out
 * the "package" line from all the source files and the code
 * should work as expected.  For an explanation of how to
 * use the Swing examples as-is from the command line, see
 * http://docs.oracle.com/javase/javatutorials/tutorial/uiswing/start/compile.html#package
 */


/*
 * HelloWorldSwing.java requires no other files. 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EtchedBorder;
import java.util.*;
import javax.swing.*; 
import javax.swing.JOptionPane;




public class MainGridFrame extends JFrame {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
	
	SerialTest serCon = null;
	private JPanel north;
	private JButton[][] gridButtons = new JButton[16][8];
	private ArrayList<Drop> drops = new ArrayList<Drop>();
	int mode = 0;
	Drop currentDrop = null;
	ArrayList<byte[]> pathData;
	
	
	public MainGridFrame( SerialTest serialConnection) {
		serCon = serialConnection;
		initUI();
	}
	
    private void initUI() {

        setTitle("Auto Drop");
        setSize(1000, 1000);   
        //setSize(250, 100);  //Andrew
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        drops.add(new Drop(0, 0, 0));
        currentDrop = drops.get(0);
        mode = 2;
        createLayout();
        /*on exit, close the serial port*/
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closing serial conection");
                serCon.close();
                e.getWindow().dispose();
            }
        });
    }
    
    private void createNorthPanel() {
		north = new JPanel();
		north.setPreferredSize(new Dimension(1000, 500));
		north.setLayout(new GridLayout(8, 16));
		north.setBorder(new EtchedBorder());
		
		class GridListener implements ActionListener {
			private int x;
			private int y;
			public GridListener(int x, int y) {
				super();
				this.x = x;
				this.y = y;
			}
			
			public void actionPerformed(ActionEvent event) {
	            JButton b = gridButtons[x][y];
	            b.setBackground(Color.red);
	            if (mode == 2) {
	            	byte xMove, yMove;
	            	xMove = (byte)(x - currentDrop.getX());
            		yMove = (byte)(y - currentDrop.getY());

	            	if (xMove > 0) {   		
	            		int temp = currentDrop.getX();
	            		while (temp != x) {
	            			temp++;
	            			JButton b1 = gridButtons[temp][currentDrop.getY()];
	            			b1.setBackground(Color.yellow);
	            		}
	            	}
	            	else {
	            		int temp = currentDrop.getX();
	            		while (temp != x) {
	            			temp--;
	            			JButton b1 = gridButtons[temp][currentDrop.getY()];
	            			b1.setBackground(Color.yellow);
	            		}
	            	}
	            	
	            	if (yMove > 0) {
	            		int temp = currentDrop.getY();
	            		while (temp != y - 1) {
	            			temp++;
	            			JButton b1 = gridButtons[x][temp];
	            			b1.setBackground(Color.yellow);
	            		}
	            	}
	            	else {
	            		int temp = currentDrop.getY();
	            		while (temp != y - 1) {
	            			temp--;
	            			JButton b1 = gridButtons[x][temp];
	            			b1.setBackground(Color.yellow);
	            		}
	            	}
	            	
	            	byte curr[] = {(byte)0x0F, 'd', (byte)currentDrop.getId(), xMove, yMove};
	            	pathData.add(curr);
	            	b.setBackground(Color.red);
	            }
	            
			}
		}
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 16; x++) {
				JButton b = new JButton(x + ", " + y);
				b.setBackground(Color.blue);
				b.setEnabled(true);
				gridButtons[x][y] = b;
				b.addActionListener(new GridListener(x, y));
				north.add(b);
			}
		}
		getContentPane().add(north, BorderLayout.NORTH);
		
	}

    private void createLayout() {
        
    	createNorthPanel();   //Andrew
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        
        /*needs to be refactored*/
        JButton dummySend = new JButton("Send Path info");
        dummySend.addActionListener(new ClickAction());

        JButton moveDropButton = new JButton("Move Drops");
        moveDropButton.addActionListener(new MoveDropAction());
        topPanel.add(moveDropButton, BorderLayout.NORTH);
        topPanel.setBackground(Color.gray);
        topPanel.setPreferredSize(new Dimension(250, 150));
        
        bottomPanel.add(topPanel);
        bottomPanel.add(dummySend, BorderLayout.NORTH);

        add(bottomPanel);

        setTitle("Border Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //pack();
    }     
    
    private class ClickAction extends AbstractAction {
        
        @Override
        public void actionPerformed(ActionEvent e) {
        	byte[] sendData = {(byte) 0x0F, 'd', 1, -1, 1, 3, 23};
        	//byte[] sendData = {(byte) 0x0F, 'd', 0, 2, 1, '\n'};
            serCon.writeData(sendData);
        }
    }
    private class MoveDropAction extends AbstractAction {
        
        @Override
        public void actionPerformed(ActionEvent e) {
        	byte[] sendData = {(byte) 0x0E};
            serCon.writeData(sendData);
        }
    }
    
}