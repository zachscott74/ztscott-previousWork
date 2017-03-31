import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EtchedBorder;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;




public class MainGridFrame extends JFrame {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
	
	SerialTest serCon = null;
	private JPanel north, south;
	private JButton[][] gridButtons = new JButton[16][8];
	private ArrayList<Drop> drops = new ArrayList<Drop>();
	int mode = 0;
	Drop currentDrop = null;
	ArrayList<byte[]> pathData = new ArrayList<byte[]>();
	DefaultListModel<String> listModel;
	DefaultListModel<String> historyModel;

    JList<String> history;

	
	
	public MainGridFrame( SerialTest serialConnection) {
		serCon = serialConnection;
		initUI();
	}
	
	private void recolor() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 16; x++) {
				JButton b = gridButtons[x][y];
				if (b.getBackground() == Color.yellow) {
					b.setBackground(Color.cyan);
				}
				
			}
		}
	}
	
	private void colorSetUp() {
		for (Drop d : drops) {
			JButton b = gridButtons[d.getX()][d.getY()];
			b.setBackground(Color.green);
			b.setOpaque(true);
		}	
	}
	
	private void resetColor() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 16; x++) {
				JButton b = gridButtons[x][y];
				if (b.getBackground() != Color.green) {
					b.setBackground(Color.blue);
				}
				
			}
		}
	}
	
	private void colorBlue() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 16; x++) {
				JButton b = gridButtons[x][y];
				b.setBackground(Color.blue);				
			}
		}
	}
	
	private void delLastColor() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 16; x++) {
				JButton b = gridButtons[x][y];
				if (b.getBackground() == Color.yellow) {
					b.setBackground(Color.blue);
				}
				
			}
		}
	}
	
    private void initUI() {

        setTitle("Auto Drop");
        setSize(1000, 1000);   
        //setSize(250, 100);  //Andrew
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //currentDrop = drops.get(0);
      
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
				if (mode == 2) {
					if (drops.size() < 5) {
						drops.add(new Drop(drops.size(), x, y));
						String temp = "Drop  " + (drops.size() - 1) + "        Position: ("
				              	 + x + ", " + y + ")";
				              	
				        listModel.addElement(temp);
				        JButton b5 = gridButtons[x][y];
				        b5.setBackground(Color.green);
				        byte addData[] = {0x0A, 'd', (byte)(drops.size() - 1), (byte)x, (byte)y, 23};
				        System.out.println("Sending new drop: " + addData);
			            serCon.writeData(addData);
					}
					else {
						
					}

			        
					mode = 0;
				}
				else if (mode == 1 && currentDrop != null) {
	            	recolor();
		            JButton b = gridButtons[x][y];
		            b.setBackground(Color.green);
	            	byte xMove, yMove;
	            	xMove = (byte)(x - currentDrop.getX());
            		yMove = (byte)(y - currentDrop.getY());

	            	if (xMove > 0) {   		
	            		int temp = currentDrop.getX();
	            		while (temp != x) {
	            			if ((temp != x - 1) || (yMove != 0)) {
		            			temp++;
		            			JButton b1 = gridButtons[temp][currentDrop.getY()];
		            			if (b1.getBackground() == Color.green) {
		            				b1.setBackground(Color.red);
		            			}
		            			else {
		            				b1.setBackground(Color.yellow);
		            			}
	            			}
	            			else {
	            				temp++;
	            			}
	            		}
	            	}
	            	else {
	            		int temp = currentDrop.getX();
	            		while (temp != x) {
	            			if ((temp != x + 1) || (yMove != 0)) {
		            			temp--;
		            			JButton b1 = gridButtons[temp][currentDrop.getY()];
		            			if (b1.getBackground() == Color.green) {
		            				b1.setBackground(Color.red);
		            			}
		            			else {
		            				b1.setBackground(Color.yellow);
		            			}
	            			}
	            			else {
	            				temp--;
	            			}
	            		}
	            	}
	            	if (yMove == 0) {
	            		//do nothing
	            	}
	            	else if (yMove > 0) {
	            		int temp = currentDrop.getY();
	            		while (temp < y - 1) {
	            			temp++;
	            			JButton b1 = gridButtons[x][temp];
	            			if (b1.getBackground() == Color.green) {
	            				b1.setBackground(Color.red);
	            			}
	            			else {
	            				b1.setBackground(Color.yellow);
	            			}
	            		}
	            	}
	            	else {
	            		int temp = currentDrop.getY();
	            		while (temp > y + 1) {
	            			temp--;
	            			JButton b1 = gridButtons[x][temp];
	            			if (b1.getBackground() == Color.green) {
	            				b1.setBackground(Color.red);
	            			}
	            			else {
	            				b1.setBackground(Color.yellow);
	            			}
	            		}
	            	}
	            	String hist = "Drop " + currentDrop.getId() + "Start: (" + 
	            	 currentDrop.getX() + ", " + currentDrop.getY() + ") -> End: (";
        			JButton b2 = gridButtons[currentDrop.getX()][currentDrop.getY()];
        			b2.setBackground(Color.white);

	            	currentDrop.setX(currentDrop.getX() + xMove);
	            	currentDrop.setY(currentDrop.getY() + yMove); 
	            	hist = hist + currentDrop.getX() + ", " + currentDrop.getY() + ")";
	            	String temp = "Drop  " + currentDrop.getId() + "        Position: ("
	              	 + currentDrop.getX() + ", " + currentDrop.getY() + ")";
	              	
	            	listModel.setElementAt(temp,  currentDrop.getId());
	            	historyModel.addElement(hist);
	            	byte curr[] = {'d', (byte)currentDrop.getId(), xMove, yMove};
	            	pathData.add(curr);
	            	mode = 0;
	            }
	            
			}
		}
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 16; x++) {
				JButton b = new JButton(x + ", " + y);
				b.setBackground(Color.blue);
				b.setEnabled(true);
				b.setOpaque(true);
				gridButtons[x][y] = b;
				b.addActionListener(new GridListener(x, y));
				north.add(b);
			}
		}
		colorSetUp();
		getContentPane().add(north, BorderLayout.NORTH);
		
	}

    private void createSouthPanel() {
    	
    	 
    	 JList<String> list;
    	 
    	 south = new JPanel(new BorderLayout());
    	 JPanel topSouth = new JPanel();
    	 JPanel histList = new JPanel();

    	 listModel = new DefaultListModel<String>();
    	 historyModel = new DefaultListModel<String>();
    	 for (int index = 0; index < drops.size(); index++) {
    		 String temp1 = "Drop " + index + "     Position: ("
           	  + drops.get(index).getX() + ", " + 
    		  drops.get(index).getY() + ")";
    		 
    		 listModel.addElement(temp1);
    	 }
        
         list = new JList<String>(listModel); //data has type Drop[]
         list.setBorder(BorderFactory.createTitledBorder("Current Drops"));
         list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
         list.setLayoutOrientation(JList.VERTICAL);
         list.setVisibleRowCount(-1);
         
         JScrollPane scrollPane = new JScrollPane();
         history = new JList<String>(historyModel); //data has type Drop[]
         history.setBorder(BorderFactory.createTitledBorder("Path History"));
         history.setLayoutOrientation(JList.VERTICAL);
         history.setVisibleRowCount(-1);
         historyModel.addElement("Drop: Start -> End");
         
         list.addListSelectionListener( new ListSelectionListener() {
        	 public void valueChanged(ListSelectionEvent event) {
        		 int ndx = list.getSelectedIndex();
        		 currentDrop = drops.get(ndx);
        		 
        	 }
         });
         
         JButton dummySend = new JButton("Send Path");
         JButton moveDropButton = new JButton("Create Path");
         JButton endPath = new JButton("End Path");
         JButton addDrop = new JButton("Add Drop");

         
         JButton deleteAll = new JButton("Delete All");
         JButton deleteLast = new JButton("Delete Last");
         
         class CreateListener implements ActionListener {
 			
 			public void actionPerformed(ActionEvent event) {
 				if (mode == 0) {
 					mode = 1;
 				}
 				else {
 					System.out.println("Already creating a path");
 				}
 			}
         }
         
         moveDropButton.addActionListener(new CreateListener());
         dummySend.addActionListener(new ClickAction());
         endPath.addActionListener(new MoveDropAction());
         deleteAll.addActionListener(new DeleteAllAction());
         deleteLast.addActionListener(new DeleteOneAction());
         addDrop.addActionListener(new addAction());
         
         south.add(list, BorderLayout.NORTH);
        
        // south.add(centerSouth, BorderLayout.WEST);
         //south.add(dummySend, BorderLayout.CENTER);
         topSouth.add(addDrop);
         topSouth.add(moveDropButton);
         topSouth.add(dummySend);
         topSouth.add(endPath);
         histList.add(history);
         histList.add(deleteAll);
         histList.add(deleteLast);
         scrollPane.setViewportView(histList);

         south.add(topSouth, BorderLayout.WEST);
         south.add(scrollPane, BorderLayout.EAST);
         getContentPane().add(south, BorderLayout.CENTER);
    }
    
    private void createLayout() {
        
    	createNorthPanel();   //Andrew
    	createSouthPanel();
    }     
    
    private byte[] concat(byte[] a, byte[] b) {
    	int aLength = a.length;
    	int bLength = b.length;
    	byte[] c = new byte[aLength + bLength];
    	System.arraycopy(a, 0, c, 0, aLength);
    	System.arraycopy(b, 0, c, aLength, bLength);
    	return c;
    }
    private class ClickAction extends AbstractAction {
        
        @Override
        public void actionPerformed(ActionEvent e) {
        	byte[] sendData = {(byte) 0x0F};
        	for (byte[] bty : pathData) {
        		sendData = concat(sendData, bty);
        	}
        	byte[] end1 = {(byte)23};
        	sendData = concat(sendData, end1);
        	System.out.println(Arrays.toString(sendData));
        	
        	byte[] end = {(byte)0x0E};
        	sendData = concat(sendData, end);
        	//byte[] sendData = {(byte) 0x0F, 'd', 0,3,-3,3,-3,3,-3,3,-3,3,23};
            serCon.writeData(sendData);
            
        }
    }
    private class MoveDropAction extends AbstractAction {
        
        @Override
        public void actionPerformed(ActionEvent e) {
        	byte[] sendData = {(byte) 0x0E};
            serCon.writeData(sendData);
            pathData.clear();
            historyModel.removeAllElements();
            resetColor();
            for (Drop d : drops) {
            	d.setXStart(d.getX());
            	d.setYStart(d.getY());
            }
        }
    }
    
 private class addAction extends AbstractAction {
        
        @Override
        public void actionPerformed(ActionEvent e) {
        	mode = 2;
        	//byte temp[] = {0x0A, 'd', 0, 0, 0, 23};
        	//serCon.writeData(temp);
        }
    }
    
    private class DeleteOneAction extends AbstractAction {
    	
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		byte[] temp;
    		if (pathData.size() != 0) {
    			temp = pathData.get(pathData.size() - 1);
    			Drop d = drops.get(temp[1]);
    			
    			JButton b = gridButtons[d.getX()][d.getY()];
    			b.setBackground(Color.blue);

    			d.setX(d.getX() - temp[2]);
    			d.setY(d.getY() - temp[3]);
    			pathData.remove(pathData.size() - 1);
    			historyModel.remove(history.getModel().getSize() - 1);
    			delLastColor();
    			b = gridButtons[d.getX()][d.getY()];
    			b.setBackground(Color.green);
    			
    			if (pathData.size() != 0) {
	    			temp = pathData.get(pathData.size() - 1);
	    			d = drops.get(temp[1]);
	    			int x = temp[2];
	    			int y = temp[3];
	    			int yy = y;
	    			
	    			while (y > 0) {
	    				b = gridButtons[d.getX()][d.getY() - y];
	    				b.setBackground(Color.yellow);
	    				y--;
	    			}
	    			while (y < 0) {
	    				b = gridButtons[d.getX()][d.getY() - y];
	    				b.setBackground(Color.yellow);
	    				y++;
	    			}
	    			int tY = d.getY() - yy;
	    			int ndx = 0;
	    			
	    			while (x > 0) {
	    				b = gridButtons[d.getX() - ndx][tY];
	    				b.setBackground(Color.yellow);
	    				x--;
	    				ndx++;
	    			}
	    			ndx = 0;
	    			while (x < 0) {
	    				b = gridButtons[d.getX() + ndx][tY];
	    				b.setBackground(Color.yellow);
	    				x++;
	    				ndx++;
	    			}	
    			}    			
    		}
    		for (Drop d: drops) {   			
    			String temp2 = "Drop  " + d.getId() + "        Position: ("
   	              	 + d.getX() + ", " + d.getY() + ")";
   	              	
   	            listModel.setElementAt(temp2,  d.getId());
    		}
    		
    	}
    }
    
    private class DeleteAllAction extends AbstractAction {
    	
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		for (Drop d: drops) {
    			d.setX(d.getXStart());
    			d.setY(d.getYStart());
    			
    			String temp = "Drop  " + d.getId() + "        Position: ("
   	              	 + d.getX() + ", " + d.getY() + ")";
   	              	
   	            listModel.setElementAt(temp,  d.getId());
    		}
    		colorBlue();
    		colorSetUp();
    		historyModel.removeAllElements();
    		pathData.clear();
    	}
    }
    
}