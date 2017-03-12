import java.awt.BorderLayout;

import javax.swing.*;

public class JPerson {
	private String name;
	private String[] topics;
	private String topic;
	
	public JPerson(Person p){
		name = p.getName();
		topics = p.getTopics();
		for(int i=0;i<topics.length;i++)
			topics[i] = capitalize(topics[i]);
		topic = "";
		for(int i=0;i<topics.length;i++)
			topic+=(i+1)+": "+capitalize(topics[i])+"\n";
	}
	public static String capitalize(String s){
		return s.substring(0,1).toUpperCase()+s.substring(1).toLowerCase();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%-15s%20s", name, topic);
	}

}
