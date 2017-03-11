import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class JGroup extends JFrame {

	public JGroup(Person[] people, String title) throws HeadlessException {
		
		setTitle(JPerson.capitalize(title)+" "+people.length+" people");
		setSize(200,300);
		setLayout(new BoxLayout(this.getContentPane(),BoxLayout.PAGE_AXIS));
		ArrayList<Person> ppl = new ArrayList<Person>();
		for(Person p:people)
			if(p!=null)
				ppl.add(p);
		Collections.sort(ppl);
		
		DefaultListModel listMod = new DefaultListModel();
		for(Person p: ppl)
			listMod.addElement(new JPerson(p).toString());
		add(new JLabel(JPerson.capitalize(title)));
		JScrollPane pane = new JScrollPane();
		pane.setViewportView(new JList(listMod));
		add(pane);
		
		setVisible(true);
	}

}
