package macro.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import macro.macroInfo;
import macro.method.SetConsole;
import macro.method.method;
import macro.packet.packet;
import macro.packet.packet.selectRoomIdListener;

import com.makeshop.android.manager.ObserverManager;

public class main_ui extends JFrame implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6290318132923809721L;
	public static final String MACRO = "MACRO";
	
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
	
	JButton Charg1, Charg2, Charg3, Nomal, Enchance, Present, Del;
	
	JComboBox<String> EtherNet = new JComboBox<String>();
	JLabel RoomTF;
	JButton RoomInfo;
	JButton HeaderCatch;
	
	JTextField InputRoom = new JTextField();
	
	JRadioButtonMenuItem country1, country2;
	
	Thread th_charg1, th_charg2, th_charg3, th_nomal, th_enchance, th_present, th_del;
	
	menuActionListener mMenuActionListener = new menuActionListener();
	macroActionListener mMacroActionListener = new macroActionListener();
	
	method mMethod;
	packet mPacket;
	
	private  String strHeader = "";
	
	public main_ui() {
		
		mPacket = new packet();
		ObserverManager.getInstance().addObserver(MACRO, this);

		Container mContainer = getContentPane();
		JMenuBar mb = new JMenuBar();
		setLayout(new BorderLayout());
		mb.add(setMenu());
		
		ButtonGroup btnGroup;
		country1=new JRadioButtonMenuItem("Korea",true);
		country2=new JRadioButtonMenuItem("Japan");
		btnGroup = new ButtonGroup();
		btnGroup.add(country1);
		btnGroup.add(country2);
		country1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				macroInfo.URL = macroInfo.KURL;
			}
		});
		country2.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				macroInfo.URL = macroInfo.JURL;
			}
		});
		
		mb.add(country1);
		mb.add(country2);
		
		setJMenuBar(mb);
		
		mContainer.add(setTextAreaJPanel() ,BorderLayout.CENTER);
		mContainer.add(setMacromJPanel() ,BorderLayout.WEST);
		mContainer.add(setCardJPanel(), BorderLayout.EAST);
		
		btn_disable();
		
		pack();
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	JPanel setMacromJPanel(){
		JPanel mJPanel = new JPanel();
		mJPanel.setLayout(new GridLayout(12, 1));
		
		Charg1 = new JButton("충전X1");
		Charg2 = new JButton("충전X2");
		Charg3 = new JButton("충전X3");
		Nomal = new JButton("노멀");
		Enchance = new JButton("강화");
		Present = new JButton("선물");
		Del = new JButton("돈낭비");
		
		RoomTF = new JLabel("roomID",JLabel.CENTER);
		RoomInfo = new JButton("방정보");
		HeaderCatch =new JButton("헤더/카드");

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
						public void select(String str){
							InputRoom.setText(str);
							RoomTF.setText(str);
							String strURL = "", strParams = "";
							if (str.charAt(0) == 'C'){
								strURL = macroInfo.URL + "/net/checkBattleInColosseum.php";
								strParams = "colosseumRoomID="+ str.substring(1);
								SetConsole.setSyso("===IN COLOSSEUM ROOM===");
							} else {
								if(str.charAt(0) == 'B'){
									SetConsole.setSyso("===IN BATTLE ROOM===");
									return;
								}
								SetConsole.setSyso("===ROOMID ERROR===");
								return;
							}
							String tmp = mMethod.getCardList(strParams, strURL).trim();
							mCardArea.setText(tmp);
							addCardList();
						}

						@Override
						public void header(String str) {
							strHeader = str;
						}
					} , mMethod.getContent().get(0) , EtherNet.getSelectedIndex());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		HeaderCatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!strHeader.equals("")) {
					mHeaderArea.setText(strHeader);
				}
				String strURL = macroInfo.URL + "/net/getDeckList.php";
				String strParams = "nop=nop";
				String tmp = mMethod.getCardList(strParams, strURL).trim();
				mCardArea.setText(tmp);
				addCardList();
			}
		});
		
		JButton set_btn = new JButton("셋팅");
		set_btn.setForeground(Color.RED);
		set_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onSetting();
			}
		});
		
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
		mJPanel.add(HeaderCatch);
		
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
		
		return mJPanel; 
	}
	
	JMenu setMenu(){
		JMenu file = new JMenu( "  File  " );
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
		cardListModel.addElement("card name:id");
		cardList = new JList<String>(cardListModel);
		
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
		
		Send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(mMethod == null) return;
				String strRoomID, strCardID, strURL ,strSkillID;
				strRoomID = InputRoom.getText().trim().substring(1);
				strCardID = getSelectCardID();
				strSkillID = getSelectSkillID();
				
				if(InputRoom.getText().trim().charAt(0) == 'C'){
					strURL = macroInfo.URL + "/net/sendColosseumCard.php";
				} else if(InputRoom.getText().trim().charAt(0) == 'B'){
					strURL = macroInfo.URL + "/net/sendBattleCard.php";
				} else {
					SetConsole.setSyso("===ROOMID ERROR===");
					return;
				}
					
				mMethod.sendCard(strRoomID, strCardID, strSkillID, strURL);
			}
		});
		
		cardList.setSelectionMode(1);
		JScrollPane scrollPane = new JScrollPane(cardList);
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
				onSetting();
			}
			else if( cmd.equals( "Exit" )) System.exit( 0 );
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
				
				if (isRunable(Del) && !getSelectCardID().equals("")) {
					th_del = new Thread(mMethod.delMoney(getSelectCardID()));
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
	
	protected String getSelectSkillID(){
		if(skillgroup.getSelection() == null) return"";
		String tmp = skillgroup.getSelection().getActionCommand().split(":")[0].trim();
		int index = Integer.parseInt(tmp);
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
	
	/**
	 * @return TraCardID (ex : 2134)
	 */
	protected String getSelectCardID(){
		if(cardList.getSelectedIndex() == -1) return "";
		String tmp = cardListModel.getElementAt(cardList.getSelectedIndex());
		return tmp = tmp.contains(":") ? tmp.split(":")[1].trim() : "" ;
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
		RoomInfo.setEnabled(true);
		RoomTF.setEnabled(true);
		HeaderCatch.setEnabled(true);
		EtherNet.setEnabled(true);
		country1.setEnabled(true);
		country2.setEnabled(true);
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
		HeaderCatch.setEnabled(false);
		EtherNet.setEnabled(false);
		country1.setEnabled(false);
		country2.setEnabled(false);
	}
	
	protected boolean isRunable(JButton jbtn){
		if(jbtn.getBackground().equals(new Color(238, 238, 238))){
			jbtn.setBackground(Color.RED);
			country1.setEnabled(false);
			country2.setEnabled(false);
			return true;
		}
		jbtn.setBackground(new Color(238, 238, 238));
		return false;
	}
	
	protected void onSetting(){
		if(mHeaderArea.getText().equals("")) 
			return;
		addCardList();
		addEtherNet(mPacket.getDevice());
		mMethod = new method(mHeaderArea.getText());
		btn_enable();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		mLogArea.setText(SetConsole.getSyso());
	}
}
