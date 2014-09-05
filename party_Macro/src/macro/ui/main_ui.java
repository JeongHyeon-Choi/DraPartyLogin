package macro.ui;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class main_ui extends JFrame{
	final JFileChooser fc = new JFileChooser(".");
	final static int EOF = -1;
	
	JList cardList;
	JMenu mJMenu = new JMenu();
	JMenuItem mJMenuItem = new JMenuItem();
	JTextArea mHeaderArea = new JTextArea();
	JMenuItem new_doc, open, save, exit, help;
	
	JButton mStartButton = new JButton("Start");
	JButton mStopButton = new JButton("Stop");
	
	menuActionListener mMenuActionListener = new menuActionListener();
	
	public main_ui() {
		Container mContainer = getContentPane();
		JMenuBar mb = new JMenuBar();
	
		mHeaderArea.setTabSize(3);
		mHeaderArea.setLineWrap(true);
		mHeaderArea.setWrapStyleWord(true);
		
		JScrollPane scrollPane = new JScrollPane(mHeaderArea);
		mb.add(setMenu());
		setJMenuBar(mb);
		
		mContainer.add(scrollPane ,"Center");
		mContainer.add(setMacromJPanel() ,"West");
		mContainer.add(setCardJPanel(), "East");
		
		pack();
		setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2), 
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	JPanel setMacromJPanel(){
		JPanel mJPanel = new JPanel();
		mJPanel.setLayout(new GridLayout(8, 1));
		
		
		JButton Charg1 = new JButton("충전X1");
		JButton Charg2 = new JButton("충전X2");
		JButton Charg3 = new JButton("충전X3");
		JButton nomal = new JButton("노멀");
		JButton enchance = new JButton("강화");
		JButton present = new JButton("선물");
		Charg1.setSize(5, 5);
		mJPanel.add(Charg1);
		mJPanel.add(Charg2);
		mJPanel.add(Charg3);
		mJPanel.add(nomal);
		mJPanel.add(enchance);
		mJPanel.add(present);
		
		return mJPanel; 
	}
	
	JMenu setMenu(){
		JMenu file = new JMenu( "File" );
		file.add(new_doc = new JMenuItem( "New" ) );
		file.add(open = new JMenuItem( "Open" ) );
		file.add(save = new JMenuItem( "Save" ) );
		file.addSeparator();
		file.add(exit = new JMenuItem( "Exit",'X' ) );
		new_doc.addActionListener( mMenuActionListener );
		open.addActionListener( mMenuActionListener );
		save.addActionListener( mMenuActionListener );
		exit.addActionListener( mMenuActionListener );
		
		return file; 
	}
	
	
	JPanel setCardJPanel(){
		JPanel mJPanel = new JPanel();
		
		mJPanel.setLayout(new GridBagLayout());
		String[] a = {"1","2","3","4","5","6","7","8","9","10","11","12","13"} ;
		cardList = new JList<String>(a);
		JTextField InputRoom = new JTextField("RoomId");
		JButton Send = new JButton("-----------내기-----------");
		JRadioButton skill1 = new JRadioButton("1");
		JRadioButton skill2 = new JRadioButton("2");
		JRadioButton skill3 = new JRadioButton("3");
		JRadioButton skill4 = new JRadioButton("4");
		JRadioButton skill5 = new JRadioButton("5");
		
		cardList.setSelectionMode(1);
		JScrollPane scrollPane = new JScrollPane(cardList);
		
		cardList.addListSelectionListener(new cardListListener());
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		mJPanel.add(InputRoom, c);
		c.gridx = 0;
		c.gridy = 1;
		mJPanel.add(Send, c);
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 2;
		mJPanel.add(scrollPane, c);
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 3;
		mJPanel.add(skill1, c);
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 4;
		mJPanel.add(skill2, c);
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 5;
		mJPanel.add(skill3, c);
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 6;
		mJPanel.add(skill4, c);
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 7;
		mJPanel.add(skill5, c);
		
		
		return mJPanel; 
	}
	class menuActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if( cmd.equals( "New" )) mHeaderArea.setText("");
			
			else if( cmd.equals( "Open" ) ) {
				int returnVal = fc.showOpenDialog( main_ui.this );
				if ( returnVal == JFileChooser.APPROVE_OPTION ) {
					File file = fc.getSelectedFile();
					try{
						FileReader f = new FileReader( file );
						StringBuffer sb = new StringBuffer();
						int ch;
						while( ( ch = f.read() ) != EOF ) sb.append( (char)ch );
						f.close();
						mHeaderArea.setText( sb.toString() );
					} catch( IOException IOe ){
						JDialog dialog = new JDialog( main_ui.this, "Error", true );
						dialog.setVisible(true);
					}
				}}
			else if( cmd.equals( "Save" ) ) {
				int returnVal = fc.showSaveDialog(main_ui.this );
				if ( returnVal == JFileChooser.APPROVE_OPTION ) {
					File file = fc.getSelectedFile();
					try{
						FileWriter f = new FileWriter( file );
						f.write( mHeaderArea.getText() );
						f.close();
					} catch( IOException IOe ){
						JDialog dialog = new JDialog( main_ui.this, "Error", true );
						dialog.setVisible(true);
					}
				}
			}
			else if( cmd.equals( "Exit" )) System.exit( 0 );
		}
		
	}

	class btnActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class cardListListener implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent event) {
			System.out.println(event);
			System.out.println(cardList.getSelectedIndex());
		}


	}
	
}
