
public class Topic implements Comparable<Topic>{
	private String title;
	private int value;
	private int numberOnes;
	
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getValue() {
		return value;
	}


	public void addValue(int value) {
		this.value += value;
	}
	public void addValue(int value,Void v){
		this.value += value;
		numberOnes++;
	}


	public Topic(String title, int value) {
		super();
		this.title = title;
		this.value = value;
		numberOnes=0;
	}
	public Topic(String title, int value, Void v) {
		super();
		this.title = title;
		this.value = value;
		numberOnes=1;
	}
	public int getOnes(){
		return numberOnes;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
//		if(!(obj instanceof Topic))
//			return false;
		return ((Topic)obj).getTitle().toLowerCase().equals(title.toLowerCase());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return title+" Popularity: "+value;
	}
	
	@Override
	public int compareTo(Topic t) {
		// TODO Auto-generated method stub
		return t.getValue()-value;
	}

}
