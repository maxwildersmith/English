import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InputWindow extends JFrame {

	private JButton go, reset, add, addStudent;
	private JTextField name;
	private JTextArea topics;
	private JScrollPane scroll;
	private JFrame in;
	private ArrayList<Person> people;
	private DefaultListModel listMod;
	private JSpinner number;
	
	public InputWindow() throws HeadlessException {
		setTitle("Input");
		setSize(400,400);
		setMaximumSize(new Dimension(400,400));
		setLayout(new BoxLayout(this.getContentPane(),1));
		
		
		number = new JSpinner(new SpinnerNumberModel(1,1,10,1));
		
		listMod = new DefaultListModel();
		
		people = new ArrayList<Person>();
		
		addStudent = new JButton("Add Students");
		addStudent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddStudent(InputWindow.this);
			}
		});
		go = new JButton("Compute");
		go.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(people.size()==0)
					return;
				Object[] groups = calculate(people,(Integer)number.getValue());
				for(int i=0;i<((Person[][])groups[0]).length;i++)
					new JGroup(((Person[][])groups[0])[i],((Topic[])groups[1])[i].getTitle());
			}
		});
		reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listMod.clear();
				people.clear();
				scroll.setViewportView(new JList(listMod));
			}
		});
		
		JList list = new JList(listMod);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel model = (ListSelectionModel)e.getSource();
				for(int i= model.getMinSelectionIndex();i<model.getMaxSelectionIndex();i++)
					if(model.isSelectedIndex(i))
						new AddStudent(InputWindow.this,people.get(i));
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scroll = new JScrollPane();
		
		//list.setSize(200, 100);
		scroll.setMaximumSize(new Dimension(700,100));
		number.setMaximumSize(number.getPreferredSize());
		add(scroll);
		add(new JLabel("Group size:"));
		add(number);
		add(addStudent);
		add(go);
		add(reset);
		
		getRootPane().setDefaultButton(go);
		
		setVisible(true);
	}

	
	protected Object[] calculate(ArrayList<Person> people2, Integer value) {
		Matchmaker m = new Matchmaker(value);
		for(Person p:people2)
			m.add(p);
		Object[] out = new Object[2];
		out[0] = m.evolveGroups();
		out[1] = m.getMostPop();
		return out;
		
	}


	public void addStudent(Person p){
		people.add(p);
		listMod.clear();
		Collections.sort(people);

		for(Person x: people)
			listMod.addElement(new JPerson(x));
		scroll.setViewportView(new JList(listMod));
		//System.err.println(scroll.getComponentCount()+"  "+scroll.getViewport().getComponentCount());
		
	}

	public void changeStudent(Person p){
		people.set(people.indexOf(p),p);
		listMod.clear();
		Collections.sort(people);
		for(Person x: people)
			listMod.addElement(new JPerson(x).toString());
		scroll.setViewportView(new JList(listMod));
	}
	
	

}
