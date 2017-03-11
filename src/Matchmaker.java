import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Matchmaker {
	Person[] people;
	int groupSize;
	Topic[] topics;
	
	public static void main(String[] args){
		
		new InputWindow();
//		ArrayList<Topic> t = new ArrayList<Topic>();
//		t.add(new Topic("a",0));
//		t.add(new Topic("b",0));
//		t.add(new Topic("a",0));
//		t.add(new Topic("d",0));
//		//System.err.println(t.indexOf(new Topic("b",0)));
//		System.err.println(Matchmaker.nextIndex(t, 0, "a"));
//		System.err.println(Matchmaker.nextIndex(t, 1, "a"));
//		System.err.println(Matchmaker.nextIndex(t, 2, "a"));
//		System.err.println(Matchmaker.nextIndex(t, 3, "a"));
//		
		/*
		Matchmaker m = new Matchmaker(2);
		m.add(new Person("Person a",new String[]{"trees","politics","communism"}));
		m.add(new Person("Person aa",new String[]{"trees","politics","communism"}));
		m.add(new Person("Person b",new String[]{"communism","politics","trees"}));
		m.add(new Person("Person bb",new String[]{"trees","communism","communism"}));
		m.add(new Person("Person c",new String[]{"communism","politics","trees"}));
		m.add(new Person("Person cc",new String[]{"thing","trees","politics"}));
		
		Person[][] g = {
				{new Person("a",new String[]{"tree","plant","ant"}),null},
				{new Person("b",new String[]{"plant","ant","tree"}),null},
				{null,null}
				};
		

		
//		for(Person[] p: m.halfGroupFix(g, new Topic[]{new Topic("tree",0),new Topic("plant",0),new Topic("ant",0)}))
//			System.out.println(Arrays.toString(p));
		
		
		
		for(Person[] p:m.makeGroups())
			System.out.print(Arrays.toString(p));
		System.out.println("\n"+Arrays.toString(m.getMostPop())+" with fitness of "+m.getFitness(m.makeGroups())+"\n\n");
		
		Person[][] g2 = m.evolveGroups();
		for(Person[] p:g2)
			System.out.print(Arrays.toString(p));
		System.out.println("\nwith fitness of "+m.getFitness(g2)+" and is "+m.valid(g2)+"\n\n");
		*/
		
	}
	public void add(Person person) {
		Person[] fixed = new Person[people.length+1];
		for(int i=0;i<people.length;i++)
			fixed[i] = people[i];
		fixed[people.length]=person;
		people = fixed;
	}
	public Matchmaker(int size){
		people = new Person[0];
		groupSize = size;
	}
	
	public Topic[] getMostPop(){
		ArrayList<Topic> topics = new ArrayList<Topic>();
		
		for(Person p: people)
			for(int i=0;i<p.getTopics().length;i++)
				if(i==0)
					if(!topics.contains(new Topic(p.getTopics()[i],0)))
						topics.add(new Topic(p.getTopics()[i],p.getTopics().length-i,null));
					else
						topics.get(topics.indexOf(new Topic(p.getTopics()[i],0))).addValue(p.getTopics().length-i,null);
				else
					if(!topics.contains(new Topic(p.getTopics()[i],0)))
						topics.add(new Topic(p.getTopics()[i],p.getTopics().length-i));
					else
						topics.get(topics.indexOf(new Topic(p.getTopics()[i],0))).addValue(p.getTopics().length-i);
		Collections.sort(topics);
		for(int i=0;i<topics.size();i++)
			if(topics.get(i).getOnes()/groupSize>1){
				topics.add(i+1,topics.get(i));
				i++;
			}
		System.err.println(topics.toString());
		
		return topics.toArray(new Topic[topics.size()]);
	}
	
	public String toArrString(ArrayList<Topic> t){
		String s ="{";
		for(Topic x: t)
			s+=t.toString()+", ";
		return s+"}";
	}
	
	public Person[][] makeGroups(){
		Person[][] groups = new Person[getMostPop().length][groupSize];
		ArrayList<Topic> topics = new ArrayList<Topic>();
		for(Topic t: getMostPop())
			topics.add(t);
		ArrayList<Person> free = new ArrayList<Person>();
		for(Person p: people)
			free.add(p);
		while(!free.isEmpty()){
			Person p = free.remove(0);
			for(String s:p.getTopics())
				if(!isFull(groups[topics.indexOf(new Topic(s,0))])){
					groups[topics.indexOf(new Topic(s,0))][lastIndex(groups[topics.indexOf(new Topic(s,0))])] = p;
					break;
				}
		}
		//				if(people[(i+1)*j])
		return groups;
	}
	
	public boolean isFull(Object[] x){
		for(Object o: x)
			if(o==null)
				return false;
		return true;
	}
	public boolean halfFull(Person[] p){
		if(p[0]==null)
			return false;
		return !isFull(p);
	}
	
	public boolean checkGroups(Person[][] group){
		boolean t=false;
		for(int i=0;i<group.length;i++)
			for(Person p: group[i])
				if(p==null&&!t){
					t=true;
					break;
				}
				else if(p==null&&t)
					return false;
					
		return true;
	}
	public int lastIndex(Object[] x){
		for(int i=0;i<x.length;i++)
			if(x[i]==null)
				return i;
		return -1;
	}
	public double getFitness(Person[][] group){
		Topic[] topics = getMostPop();
		Person[][] groups = group;
		double maxFit = people.length*people[0].topics.length;
		int fit =0;
		for(int i=0;i<groups.length;i++)
			for(Person p:groups[i])
				if(p!=null)
					for(int t=0;t<p.getTopics().length;t++)
						if(topics[i].equals(new Topic(p.getTopics()[t],0))){
							fit+=p.getTopics().length-t;
							break;
						}
		int halfFilled = 0;
		double fitMod=0;
		for(Person[] p:groups)
			if(halfFull(p))
				halfFilled++;
		int ppl=0;
		for(Person[] peeps:group)
			for(Person p:peeps)
				if(p!=null)
					ppl++;
		if(halfFilled>1||ppl!=people.length)
			fitMod = -.2;
		
		return fit/maxFit+fitMod;
	}
	public Person[][] evolveGroups(){
		
		ArrayList<Person> ppl = new ArrayList<Person>();
		for(Person p:people)
			ppl.add(p);

		Person[][] fin = makeGroups();
		int improv=0;
		boolean good=false;
		while(improv<people.length*1000 || !good){
			if(improv>people.length*10000)
				break;
			good=false;
			if(getFitness(fin)==1.)
				break;
			Person[][] groups = new Person[getMostPop().length][groupSize];
			Collections.shuffle(ppl);
			ArrayList<Topic> topics = new ArrayList<Topic>();
			for(Topic t: getMostPop())
				topics.add(t);
			ArrayList<Person> free = new ArrayList<Person>();
			for(Person p: ppl)
				free.add(p);
			while(!free.isEmpty()){
				Person p = free.remove(0);
				for(String s:p.getTopics())
					for(int i=topics.indexOf(new Topic(s,0));i>-1;i=nextIndex(topics,i,s)){
						if(!isFull(groups[i])){
							System.err.println("added "+p.toString()+" to "+s);
							groups[i][lastIndex(groups[i])] = p;
							break;
							}
							System.err.println(i);
							i++;
					}
			}
			for(Person[] p:groups)
				System.out.print(Arrays.toString(p));
			System.out.println("");
			ArrayList<Person[]> notFill = new ArrayList<Person[]>();
			for(int i=0;i<groups.length;i++)
				if(halfFull(groups[i]))
					notFill.add(groups[i]);
//			
//			while(notFill.size()>1){
//				ArrayList<Topic> t = new ArrayList<Topic>();
			
//				for(int i=0;i<groups.length;i++)      .....EVOLVE GROUPS METHOD.....
//					if(!halfFull(groups[i]))
//						t.add(getMostPop()[i]);
//				for(int i=0;i<notFill.size();i++){
//					if(!halfFull(notFill.get(i)))
//							notFill.remove(i);
//					for(int p=0;p<notFill.get(i).length;p++)
//						for(String topic:notFill.get(i)[p].getTopics())
//							if(!topic.equals(getMostPop()[i].getTitle())&&t.contains(new Topic(topic,0))){
//								notFill.get(i)[p]=null;
//								
//							}
//				}
//								
//			}
			improv++;
//			if(!finChange&&(getFitness(fin)+fitMod)<(getFitness(groups)+fitMod)){
//				fin = groups;
//				finChange=true;
//			}
			
			
			if(lastFill(groups)<(groups.length-1)){
				//Person[][] halfGroups = new Person[groups.length-lastFill(groups)][groupSize];
				Topic[] t = new Topic[topics.size()-lastFill(groups)];
				//System.out.println(topics.toString());
				for(int i=topics.size()-lastFill(groups);i<topics.size();i++)
					t[i-t.length] = topics.get(i);
				Person[][] halfGroups = halfGroupFix(groups, t);
				for(int i=lastFill(groups);i<groups.length;i++)
					groups[i]=halfGroups[i];
			}
				
			
			int x=0;
			for(Person[] peeps:groups)
				for(Person p:peeps)
					if(p!=null)
						x++;
			if(!(!valid(groups)||x!=people.length))
				good=true;
				
			 if(getFitness(fin)<getFitness(groups)||good)
				fin = groups;
			
		}
		
		return fin;
	}
	private int lastFill(Person[][] groups) {
		int i=0;
		for(i=0;i<groups.length;i++)
			if(halfFull(groups[i]))
				break;
		return i;
	}
	private boolean valid(Person[][] groups) {
		int halfFilled = 0;
		for(Person[] p:groups)
			if(halfFull(p))
				halfFilled++;
		return halfFilled<=1;
	}
	private Person[][] halfGroupFix(Person[][] groups, Topic[] topics){
		ArrayList<Person> ppl = new ArrayList<Person>();
		ArrayList<Person> free = new ArrayList<Person>();
		Person[][] fixedGroup = groups;
		double baseFit = getFitness(groups);
		boolean bad = true;
		for(Person[] group: groups)
			for(Person p: group)
				if(p!=null){
					ppl.add(p);
				}
		free.addAll(ppl);
		int mod = 0;
		while(bad){
			//System.out.println(free.size());
			fixedGroup = new Person[groups.length][groups[0].length];
			Collections.shuffle(free);
			for(int p=0;p<free.size();p++)
				for(int i=0;i<fixedGroup.length;i++)
					if(lastIndex(fixedGroup[i])>-1){
						fixedGroup[i][lastIndex(fixedGroup[i])] = free.get(p);
						break;
					}
			//for(Person[] ss: fixedGroup)
				//System.out.println(Arrays.toString(ss)+"   mod= "+mod);
//			bad = true;
//			fixedGroup = new Person[groups.length][groups[0].length];
//			free.clear();
//			free.addAll(ppl);
//			for(Person p: free){
//				for(int i=mod;i<free.get(0).getTopics().length;i++){
//					System.out.println(i+"");
//					if(free.indexOf(p)>-1)
//						for(int t=0; t<topics.length;t++)
//							if(topics[t].equals(new Topic(p.getTopics()[i],0))){
//								for(Person[] ss: fixedGroup)
//									System.out.println(Arrays.toString(ss)+"   mod= "+mod);
//								System.out.println();
//								fixedGroup[t][lastIndex(fixedGroup[t])] = free.remove(free.indexOf(p));
//								System.out.println(p.toString());
//								break;
//							}
//				}
//			}
			
			if(valid(fixedGroup)){
				bad = false;
				mod++;
			}
				
		}
		
		return fixedGroup;
	}
	public static int nextIndex(ArrayList<Topic> s, int index, String word){
		List<Topic> remaining = s.subList(index,s.size());
		System.out.println(word+":  "+remaining.toString()+"  "+remaining.indexOf(new Topic(word,0)));
		if(remaining.indexOf(new Topic(word,0))>=0)
			return index+remaining.indexOf(new Topic(word,0));
		return -1;
	}
	
	
}
