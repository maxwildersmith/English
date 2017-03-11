import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddStudent extends JFrame {

	private JTextField name;
	private JTextArea topics;
	private JButton addP;
	private InputWindow parent;
	
	public AddStudent(InputWindow win) throws HeadlessException {
		parent = win;
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS));
		setTitle("Add Student");
		name = new JTextField();
		name.setColumns(20);
		name.setMaximumSize(name.getPreferredSize());
		
		topics = new JTextArea();
		//topics.setBorder(name.getBorder()); 
		
		topics.setColumns(20);
		
		addP = new JButton("Add");
		addP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(name.getText().trim().length()==0||topics.getText().trim().length()==0)
					return;
				parent.addStudent(new Person(name.getText().toString(),topics.getText().toString().split(",")));
				name.setText("");
				topics.setText("");
			}
		});
		this.getRootPane().setDefaultButton(addP);
		setSize(150,200);
		setMaximumSize(new Dimension(150,200));
		add(new JLabel("Name: "));
		add(name);
		add(new JLabel("Topics seperated \',\': "));
		
		topics.setLineWrap(true);
		topics.setWrapStyleWord(true);
		topics.setSize(200, 100);
		JScrollPane pane = new JScrollPane();
		pane.setMaximumSize(new Dimension(250,100));
		pane.setViewportView(topics);
		add(pane);
		add(addP);
		
		setVisible(true);
	}
	public AddStudent(InputWindow win, boolean edit) throws HeadlessException {
		parent = win;
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS));
		setTitle("Add Student");
		name = new JTextField();
		name.setColumns(20);
		name.setMaximumSize(name.getPreferredSize());
		
		topics = new JTextArea();
		//topics.setBorder(name.getBorder()); 
		
		topics.setColumns(20);
		
		addP = new JButton("Add");
		addP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(name.getText().trim().length()==0||topics.getText().trim().length()==0)
					return;
				parent.addStudent(new Person(name.getText().toString(),topics.getText().toString().split(",")));
				name.setText("");
				topics.setText("");
			}
		});
		this.getRootPane().setDefaultButton(addP);
		setSize(150,200);
		setMaximumSize(new Dimension(150,200));
		add(new JLabel("Name: "));
		add(name);
		add(new JLabel("Topics seperated \',\': "));
		
		topics.setLineWrap(true);
		topics.setWrapStyleWord(true);
		topics.setSize(200, 100);
		JScrollPane pane = new JScrollPane();
		pane.setMaximumSize(new Dimension(250,100));
		pane.setViewportView(topics);
		add(pane);
		add(addP);
		
		setVisible(true);
	}

}
