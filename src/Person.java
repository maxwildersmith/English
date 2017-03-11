import java.io.Serializable;

public class Person implements Serializable, Comparable<Person>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5482865521948844992L;
	String name;
	String[] topics;
	
	public Person(String name, String[] topics) {
		super();
		this.name = name;
		this.topics = topics;
		for(int i=0;i<topics.length;i++)
			topics[i] = topics[i].trim().toLowerCase();
	}
	
	public Person(){
		name = "NULL";
		topics = new String[]{"NULL"};
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getTopics() {
		return topics;
	}
	public void setTopics(String[] topics) {
		this.topics = topics;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%15s%-20s", name, topics);
	}

	@Override
	public int compareTo(Person o) {
		return this.getName().compareTo(o.getName());
	}
	
}
