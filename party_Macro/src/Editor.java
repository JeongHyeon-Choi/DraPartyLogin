
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class Editor extends JFrame 
	implements ActionListener,ItemListener {
	static final long serialVersionUID=1108;
	final static int EOF = -1;

	final JFileChooser fc = new JFileChooser(".");
	JMenuItem new_doc, open, save, exit, help;
	JMenuItem copy, cut, paste, fbig, fsmall,bold;
	JComboBox fontChoice, sizeChoice;
	JCheckBoxMenuItem ch1;
	JRadioButtonMenuItem ra1, ra2;
	JTextArea ta;
	ButtonGroup btnGroup;

	int fontB=Font.PLAIN, fontSize=16;
	String fontName= "Monospaced";  // Initial Font Name
	Font font = new Font(fontName,fontB,fontSize);

	Editor() {
		super( "Simple Text Editer" );
		JMenuBar mb = new JMenuBar(); // 메뉴바 생성

		//		File 메뉴 생성
		JMenu file = new JMenu( "File" );
		file.add(new_doc = new JMenuItem( "New" ) );
		file.add(open = new JMenuItem( "Open" ) );
		file.add(save = new JMenuItem( "Save" ) );
		file.addSeparator();
		file.add(exit = new JMenuItem( "Exit",'X' ) );
		new_doc.addActionListener( this );
		open.addActionListener( this );
		save.addActionListener( this );
		exit.addActionListener( this );
		mb.add(file);
//		Edit 메뉴 생성
		JMenu edit = new JMenu( "Edit" );
		edit.add(copy = new JMenuItem( "Copy" ) );
		edit.add(cut = new JMenuItem( "Cut" ) );
		edit.addSeparator();
		edit.add(paste = new JMenuItem( "Paste" ) );
		copy.addActionListener( this );
		cut.addActionListener( this );
		paste.addActionListener( this );
		mb.add( edit );
		
		JMenu fontM = new JMenu("Font");
		fontM.add(fbig=new JMenuItem("bigger char"));
		fontM.add(fsmall=new JMenuItem("smaller char"));
		fontM.add(bold=new JMenuItem("Plain/Bold"));
		fbig.addActionListener(this);
		fsmall.addActionListener(this);
		bold.addActionListener(this);
		mb.add(fontM);
		
		String[] fontNames = {
		  "Serif","SansSerif","Dialog","DialogInput","Monospaced"};
		fontChoice = new JComboBox(fontNames);
		fontChoice.setSelectedItem("Monospaced");
		fontChoice.setToolTipText("Font Name Setting");
		fontChoice.addItemListener(this);
		mb.add(fontChoice);
		
		String[] fontSizes = { "10","12","14","16","18",
				"20","22","24","26","28","30"};
		sizeChoice = new JComboBox(fontSizes);
		sizeChoice.setToolTipText("Font Size Setting");
		sizeChoice.setSelectedItem("16");
		sizeChoice.addItemListener(this);
		mb.add(sizeChoice);
		
		JMenu styleM = new JMenu("Charactor Style");
		styleM.setBorder(new BevelBorder(BevelBorder.RAISED));
		styleM.add(ra1=new JRadioButtonMenuItem("PLAIN",true));
		styleM.add(ra2=new JRadioButtonMenuItem("BOLD"));
		btnGroup = new ButtonGroup();
		btnGroup.add(ra1);
		btnGroup.add(ra2);
		ra1.addItemListener(this);
		ra2.addItemListener(this);
		
		styleM.addSeparator();
		styleM.add(ch1=new JCheckBoxMenuItem("Italic"));
		ch1.addItemListener(this);
		mb.add(styleM);
		
		JMenu helpM = new JMenu("Help");
		helpM.add(help=new JMenuItem("[Version]"));
		help.addActionListener(this);
		mb.add(helpM);
		
		setJMenuBar( mb );
		ta = new JTextArea();
		ta.setTabSize(3); ta.setFont(font);
		getContentPane().add( new JScrollPane( ta ), "Center" );
		setSize( 480, 380 ); setLocation(180,80);
		setVisible(true);
	}
	public void actionPerformed( ActionEvent e ) {
		String cmd = e.getActionCommand();
		if( cmd.equals( "New" )) ta.setText("");
		else if( cmd.equals( "Open" ) ) {
			int returnVal = fc.showOpenDialog( Editor.this );
			if ( returnVal == JFileChooser.APPROVE_OPTION ) {
				File file = fc.getSelectedFile();
				try{
					FileReader f = new FileReader( file );
					StringBuffer sb = new StringBuffer();
					int ch;
					while( ( ch = f.read() ) != EOF ) sb.append( (char)ch );
					f.close();
					ta.setText( sb.toString() );
				} catch( IOException IOe ){
					JDialog dialog = new JDialog( this, "Error", true );
					dialog.setVisible(true);
				}
			}}
		else if( cmd.equals( "Save" ) ) {
			int returnVal = fc.showSaveDialog(Editor.this );
			if ( returnVal == JFileChooser.APPROVE_OPTION ) {
				File file = fc.getSelectedFile();
				try{
					FileWriter f = new FileWriter( file );
					f.write( ta.getText() );
					f.close();
				} catch( IOException IOe ){
					JDialog dialog = new JDialog( this, "Error", true );
					dialog.setVisible(true);
				}
			}
		}
		else if( cmd.equals( "Exit" )) System.exit( 0 );
		else if( cmd.equals( "Copy" )) ta.copy();
		else if( cmd.equals( "Cut" ))  ta.cut();
		else if( cmd.equals( "Paste" )) ta.paste();
		else if( cmd.startsWith("big")) {
			if(fontSize>=30) {
				Toolkit.getDefaultToolkit().beep();
				return;
			}
			fontSize+=2;
			ta.setFont(new Font(font.getFontName(),fontB,fontSize));
			sizeChoice.setSelectedItem(""+fontSize);
		}else if( cmd.startsWith("smal")){
			if(fontSize<=10) {
				Toolkit.getDefaultToolkit().beep();
				return;
			}
			fontSize-=2;
			ta.setFont(new Font(font.getFontName(),fontB,fontSize));
			sizeChoice.setSelectedItem(""+fontSize);
		}else if( cmd.startsWith("Plain")){
			if (fontB==Font.PLAIN) {fontB=Font.BOLD; ra2.setSelected(true);}
			else { fontB=Font.PLAIN; ra1.setSelected(true);}
			ta.setFont(new Font(fontName,fontB,fontSize));
		}else if(cmd.startsWith("[V")){
			JOptionPane.showMessageDialog(null,"Simple Editor V 1.6", "Version Info",
					JOptionPane.INFORMATION_MESSAGE);
		}
		repaint();
	}
	public static void main(String[] args)
	{	Editor fm = new Editor();
		fm.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void itemStateChanged(ItemEvent e) {
	  if(e.getSource()==fontChoice){
		String fontName = (String) e.getItem();
		font=new Font(fontName,fontB,fontSize);
		ta.setFont(font);
		return;
	  }
	  if(e.getSource()==ra1) fontB=Font.PLAIN ; 
	  else if(e.getSource()==ra2) fontB=Font.BOLD;
	  else if(e.getSource()==ch1){
		if (ch1.isSelected()) fontB=fontB +Font.ITALIC;
		else fontB=fontB-Font.ITALIC;
	  } else fontSize=Integer.parseInt((String)e.getItem());
	  
	  ta.setFont(new Font(font.getFontName(),fontB,fontSize));
	}
}