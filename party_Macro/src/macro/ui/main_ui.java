package macro.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

import macro.method.SetConsole;
import macro.method.method;
import macro.packet.packet;
import macro.packet.packet.selectRoomIdListener;

public class main_ui extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6290318132923809721L;
	
	final JFileChooser fc = new JFileChooser(".");
	final static int EOF = -1;
	
	JList<String> cardList;
	DefaultListModel<String> cardListModel = new DefaultListModel<String>();
	ButtonGroup skillgroup;
	JMenu mJMenu = new JMenu();
	JMenuItem mJMenuItem = new JMenuItem();

	JTextArea mLogArea = new JTextArea();
	JTextArea mHeaderArea = new JTextArea();
	JTextArea mCardArea = new JTextArea();
	JMenuItem new_doc, open, save, exit, help ,Setting;
	
	JButton Send = new JButton("send");;
	
	JButton Charg1 = new JButton("충전X1");
	JButton Charg2 = new JButton("충전X2");
	JButton Charg3 = new JButton("충전X3");
	JButton Nomal = new JButton("노멀");
	JButton Enchance = new JButton("강화");
	JButton Present = new JButton("선물");
	JButton Del = new JButton("돈낭비");
	
	JComboBox EtherNet = new JComboBox<String>();
	JLabel RoomTF = new JLabel("roomID");
	JButton RoomInfo = new JButton("방정보");
	
	JTextField InputRoom = new JTextField();
	
	Thread th_charg1;
	Thread th_charg2;
	Thread th_charg3;
	Thread th_nomal;
	Thread th_enchance;
	Thread th_present;
	Thread th_del;
	
	menuActionListener mMenuActionListener = new menuActionListener();
	skillActionListener mSkillActionListener = new skillActionListener();
	macroActionListener mMacroActionListener = new macroActionListener();
	
	method mMethod;
	packet mPacket;
	SetConsole mSetConsole;
	
	public main_ui() {
		
//		  try {
//	            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//	        }catch(Exception ee) {}
		mPacket = new packet();
		mSetConsole = new SetConsole();

		Container mContainer = getContentPane();
		JMenuBar mb = new JMenuBar();
		setLayout(new BorderLayout());
		mb.add(setMenu());
		setJMenuBar(mb);
		
		mContainer.add(setTextAreaJPanel() ,BorderLayout.CENTER);
		mContainer.add(setMacromJPanel() ,BorderLayout.WEST);
		mContainer.add(setCardJPanel(), BorderLayout.EAST);
		
		btn_disable();
		
		pack();
//		setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2), 
//				(int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 4));
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	JPanel setMacromJPanel(){
		JPanel mJPanel = new JPanel();
		mJPanel.setLayout(new GridLayout(11, 1));
		
		
		Charg1 = new JButton("충전X1");
		Charg2 = new JButton("충전X2");
		Charg3 = new JButton("충전X3");
		Nomal = new JButton("노멀");
		Enchance = new JButton("강화");
		Present = new JButton("선물");
		Del = new JButton("돈낭비");
		
		RoomTF = new JLabel("roomID",JLabel.CENTER);
		RoomInfo = new JButton("방정보");

		Charg1.addActionListener(mMacroActionListener);
		Charg2.addActionListener(mMacroActionListener);
		Charg3.addActionListener(mMacroActionListener);
		Nomal.addActionListener(mMacroActionListener);
		Enchance.addActionListener(mMacroActionListener);
		Present.addActionListener(mMacroActionListener);
		Del.addActionListener(mMacroActionListener);
		
		RoomInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					mPacket.Start(new selectRoomIdListener() {
						@Override
						public void select(String str) {
							InputRoom.setText(str);
							RoomTF.setText(str);
						}

						@Override
						public void header(String str) {
							mHeaderArea.setText(str);
						}
					} , mMethod.getContent().get(0) , EtherNet.getSelectedIndex());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		JButton set_btn = new JButton("셋팅");
		set_btn.setForeground(Color.RED);
		set_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!mHeaderArea.getText().equals("")){
					addCardList();
					addEtherNet(mPacket.getDevice());
					mMethod = new method(mHeaderArea.getText(), mSetConsole);
					btn_enable();
				}
			}
		});
		
		Charg1.setSize(5, 5);
		mJPanel.add(set_btn);
		mJPanel.add(Charg1);
		mJPanel.add(Charg2);
		mJPanel.add(Charg3);
		mJPanel.add(Nomal);
		mJPanel.add(Enchance);
		mJPanel.add(Present);
		mJPanel.add(Del);
		mJPanel.add(EtherNet);
		mJPanel.add(RoomTF);
		mJPanel.add(RoomInfo);
		
		return mJPanel; 
	}

	JPanel setTextAreaJPanel(){
		JPanel mJPanel = new JPanel();
		
		mJPanel.setLayout(new GridLayout(3, 1));
		mLogArea.setTabSize(4);
		mLogArea.setLineWrap(true);
		mLogArea.setWrapStyleWord(true);
		mLogArea.setEditable(false);
		mLogArea.setBackground(Color.YELLOW);
		
		mHeaderArea.setTabSize(4);
		mHeaderArea.setLineWrap(true);
		mHeaderArea.setWrapStyleWord(true);
		
		mCardArea.setTabSize(4);
		mCardArea.setLineWrap(true);
		mCardArea.setWrapStyleWord(true);
		
		JScrollPane scrollPane1 = new JScrollPane(mLogArea);
		JScrollPane scrollPane2 = new JScrollPane(mHeaderArea);
		JScrollPane scrollPane3 = new JScrollPane(mCardArea);
		
		mJPanel.add(scrollPane1);
		mJPanel.add(scrollPane2);
		mJPanel.add(scrollPane3);
		
		mSetConsole.setArea(mLogArea);
		
		return mJPanel; 
	}
	
	JMenu setMenu(){
		JMenu file = new JMenu( "File" );
		file.add(new_doc = new JMenuItem( "New" ) );
		file.add(open = new JMenuItem( "Open" ) );
		file.add(save = new JMenuItem( "Save" ) );
		file.addSeparator();
		file.add(Setting = new JMenuItem( "Set MacroInfo" ) );
		file.addSeparator();
		file.add(exit = new JMenuItem( "Exit",'X' ) );
		new_doc.addActionListener( mMenuActionListener );
		open.addActionListener( mMenuActionListener );
		save.addActionListener( mMenuActionListener );
		Setting.addActionListener(mMenuActionListener);
		exit.addActionListener( mMenuActionListener );
		return file; 
	}
	
	JPanel setCardJPanel(){
		JPanel mJPanel = new JPanel();
		
		mJPanel.setLayout(new BorderLayout());
		String[] a = {"1","2","3","4","5","6","7","8","9","10","11","12","13"} ;
		cardListModel.addElement("cardList");
		cardList = new JList<String>(cardListModel);
//		cardList = new JList<>(a);
		
		InputRoom = new JTextField();
		JRadioButton skill1 = new JRadioButton("1 : 카드화 전체");
		JRadioButton skill2 = new JRadioButton("2 : 카드화 2");
		JRadioButton skill3 = new JRadioButton("3 : 카드화 1");
		JRadioButton skill4 = new JRadioButton("4 : 스킬 절대 발동");
		JRadioButton skill5 = new JRadioButton("5 : 취소");
		skillgroup = new ButtonGroup();
		skillgroup.add(skill1);skillgroup.add(skill2);
		skillgroup.add(skill3);skillgroup.add(skill4);skillgroup.add(skill5);	
		skill1.setActionCommand("1 : 카드화 전체");
		skill2.setActionCommand("2 : 카드화 2");
		skill3.setActionCommand("3 : 카드화 1");
		skill4.setActionCommand("4 : 스킬 절대 발동");
		skill5.setActionCommand("5 : 취소");
		skill1.addActionListener(mSkillActionListener);
		skill2.addActionListener(mSkillActionListener);
		skill3.addActionListener(mSkillActionListener);
		skill4.addActionListener(mSkillActionListener);
		skill5.addActionListener(mSkillActionListener);
		Send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String tmp_cardid = cardListModel.getElementAt(cardList.getSelectedIndex());
				String skillid = (skillgroup.getSelection() == null) ? "" : getSkillId(skillgroup.getSelection().getActionCommand().split(":")[0].trim());
				if(mMethod == null) return;
				
				mMethod.SendCard(InputRoom.getText().trim(), 
						tmp_cardid.split(":")[1].trim(), 
						skillid);
//				tmp_cardid.split(":")[1].trim();
//				InputRoom.getText().trim();
//				getSkillId(skillgroup.getSelection().getActionCommand().split(":")[0].trim());
			}
		});
		
		cardList.setSelectionMode(1);
		JScrollPane scrollPane = new JScrollPane(cardList);
		cardList.addListSelectionListener(new cardListListener());
		JPanel idJPanel = new JPanel(new GridLayout(2,1));
		idJPanel.add(InputRoom);
		idJPanel.add(Send);
		
		JPanel skillJPanel = new JPanel(new GridLayout(5,1));
		skillJPanel.add(skill1);
		skillJPanel.add(skill2);
		skillJPanel.add(skill3);
		skillJPanel.add(skill4);
		skillJPanel.add(skill5);

		mJPanel.add(idJPanel, BorderLayout.SOUTH);
		mJPanel.add(scrollPane, BorderLayout.CENTER);
		mJPanel.add(skillJPanel, BorderLayout.NORTH);
		
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.fill = GridBagConstraints.BOTH;
//        mJPanel.setLayout(new GridBagLayout());
//		
//        gbc.gridy = 1;
//		gbc.gridheight = 1;
//		mJPanel.add(InputRoom,gbc);
//		gbc.gridy = 2;
//		mJPanel.add(Send);
//		gbc.gridy = 3;
//		mJPanel.add(scrollPane, gbc);
//		gbc.gridy = 4;
//		mJPanel.add(skill1,gbc);
//		gbc.gridy = 5;
//		mJPanel.add(skill2,gbc);
//		gbc.gridy = 6;
//		mJPanel.add(skill3,gbc);
//		gbc.gridy = 7;
//		mJPanel.add(skill4,gbc);
//		gbc.gridy = 8;
//		mJPanel.add(skill5,gbc);
		
		return mJPanel; 
	}
	
	class menuActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if( cmd.equals( "New" )) {
				mHeaderArea.setText("");
				mLogArea.setText("");
				mCardArea.setText("");
			}
			
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
						String tmp = sb.toString();
						tmp = convert(tmp, "UTF-8");
//						tmp.split(":spilt");
						mHeaderArea.setText(tmp.split(":split:")[0]);
						mCardArea.setText(tmp.split(":split:")[1]);
						setTitle(file.getName());
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
						f.write( mHeaderArea.getText()+":split:"+
								mCardArea.getText()+":split:"
								);
						f.close();
					} catch( IOException IOe ){
						JDialog dialog = new JDialog( main_ui.this, "Error", true );
						dialog.setVisible(true);
					}
				}
			} else if (cmd.equals("Set MacroInfo")){
				if(!mHeaderArea.getText().equals("")){
					addCardList();
					mMethod = new method(mHeaderArea.getText(), mSetConsole);
					btn_enable();
				}
			}
			else if( cmd.equals( "Exit" )) System.exit( 0 );
		}
		
	}
	class cardListListener implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
		}
	}
	class skillActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			skillgroup.getElements();
			getSkillId(skillgroup.getSelection().getActionCommand().split(":")[0].trim());
		}
		
	}
	
	class macroActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String cmd = arg0.getActionCommand();
			if(mMethod == null){
				return ;
			} else if (cmd.equals("충전X1")) {
				if (isRunable(Charg1)) {
					th_charg1 = new Thread(mMethod.getCharggacha());
					th_charg1.start();
				} else {
					th_charg1.stop();
				}
			} else if (cmd.equals("충전X2")) {
				if (isRunable(Charg2)) {
					th_charg2 = new Thread(mMethod.getCharggacha());
					th_charg2.start();
				} else {
					th_charg2.stop();
				}
			} else if (cmd.equals("충전X3")) {
				if (isRunable(Charg3)) {
					th_charg3 = new Thread(mMethod.getCharggacha());
					th_charg3.start();
				} else {
					th_charg3.stop();
				}
			} else if (cmd.equals("노멀")) {
				if (isRunable(Nomal)) {
					th_nomal = new Thread(mMethod.getNomalgacha());
					th_nomal.start();
					Del.setEnabled(false);
				} else {
					th_nomal.stop();
					Del.setEnabled(true);
				}
			} else if (cmd.equals("강화")) {
				if (isRunable(Enchance)) {
					th_enchance = new Thread(mMethod.getEvolgacha());
					th_enchance.start();
					Present.setEnabled(false);
				} else {
					th_enchance.stop();
					Present.setEnabled(true);
				}
			} else if (cmd.equals("선물")) {
				if (isRunable(Present)) {
					th_present = new Thread(mMethod.getPresnet());
					th_present.start();
					Enchance.setEnabled(false);
				} else {
					th_present.stop();
					Enchance.setEnabled(true);
				}
			} else if (cmd.equals("돈낭비")) {
				String BaseID = (cardList.getSelectedIndex() == -1) ? "" : cardListModel.getElementAt(cardList.getSelectedIndex()) ;
				if (!BaseID.contains(":"));
				else if (isRunable(Del)) {
					th_del = new Thread(mMethod.delMoney(BaseID.split(":")[1].trim()));
					th_del.start();
					Nomal.setEnabled(false);
				} else {
					th_del.stop();
					Nomal.setEnabled(true);
				}
			}
		}
		
	}
	
	void addEtherNet(ArrayList<String> ary){
		EtherNet.removeAllItems();
		for (int i = 0, cnt = ary.size(); i < cnt; i++) {
			String tmp = ary.get(i);
			if(tmp.length() > 6) tmp = tmp.substring(0, 6);
			EtherNet.addItem(tmp);
		}
	}
	
	void addCardList(){
		String tmp = mCardArea.getText();
		String[] listStr = tmp.split("\n");
		cardListModel.clear();
		for (String string : listStr) {
			cardListModel.addElement(string);
		}
	}
	
	String getSkillId(String str){
		int index = Integer.parseInt(str);
		switch (index) {
		case 1: //전체
			return "100001";
		case 2: //2
			return "100003";
		case 3: //1
			return "100002";
		case 4: //절발
			return "210001";
		case 5: ///
		default:
			break;
		}
		return "";
	}
	
	static String convert(String str, String encoding) throws IOException {
		  ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
		  requestOutputStream.write(str.getBytes(encoding));
		  return requestOutputStream.toString(encoding);
	}
	
	void btn_enable(){
		Charg1.setEnabled(true);
		Charg2.setEnabled(true);
		Charg3.setEnabled(true);
		Nomal.setEnabled(true);
		Enchance.setEnabled(true);
		Present.setEnabled(true);
		Send.setEnabled(true);
		Del.setEnabled(true);
		if(mMethod.getTitle().get(5).equals("deviceID") && mMethod.getContent().get(5).equals("000000000000000")){
			RoomInfo.setEnabled(true);
			RoomTF.setEnabled(true);
		}
	}
	
	void btn_disable(){
		Charg1.setEnabled(false);
		Charg2.setEnabled(false);
		Charg3.setEnabled(false);
		Nomal.setEnabled(false);
		Enchance.setEnabled(false);
		Present.setEnabled(false);
		Send.setEnabled(false);
		RoomInfo.setEnabled(false);
		RoomTF.setEnabled(false);
		Del.setEnabled(false);
	}
	
	boolean isRunable(JButton jbtn){
		if(jbtn.getBackground().equals(new Color(238, 238, 238))){
			jbtn.setBackground(Color.RED);
			return true;
		}
		jbtn.setBackground(new Color(238, 238, 238));
		return false;
	}
}
