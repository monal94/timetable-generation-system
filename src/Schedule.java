import java.util.*;
import java.sql.*;

class CourseData {
	String name, code, department;
	String[] stgrp, ins;
	int nostgrp, noins;
	public CourseData() {
		name = new String();
		code = new String();
		department = new String();
		stgrp = new String[100];
		ins = new String[100];
		nostgrp = 0;
		noins = 0;
	}
}

class StudentGrpData {
	String info, code;
	int strength;
	String[] course;
	public StudentGrpData() {
		info = new String();
		code = new String();
		course = new String[100];
	}
}

class InstructorData {
	String name,code;
	String[] course;
	public InstructorData() {
		name = new String();
		code = new String();
		course = new String[10];
	}
}

class ClassData {
	String department, code;
	int strength;
	public ClassData() {
		department = new String();
		code = new String();
	}
}

class InputData {
	CourseData[] course;
	ClassData[] rooms;
	StudentGrpData[] stgrp;
	InstructorData[] ins;
	int noroom, nocourse, nostgrp, noins;
	public InputData() {
		course = new CourseData[100];
		rooms = new ClassData[100];
		stgrp = new StudentGrpData[100];
		ins = new InstructorData[100];
	}

	boolean classFormat(String l) {
		StringTokenizer st = new StringTokenizer(l," ");
		if(st.countTokens()==3)
			return(true);
		else
			return(false);
	}

	public void takeInput() {
		Connection cn=null;
		Statement st=null;
		ResultSet rs=null;

		try {
			int i=0, j, k;
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			cn=DriverManager.getConnection("Jdbc:Odbc:data");
			st=cn.createStatement();

			rs=st.executeQuery("SELECT * FROM course");
			while (rs.next()) {					
				course[i]=new CourseData();
				course[i].name=rs.getString("cname");
				course[i].code=rs.getString("ccode");
				course[i].department=rs.getString("cdep");
				i++;
			}
			nocourse=i;

			rs=st.executeQuery("SELECT * FROM studentgrp");
			i=0;
			while(rs.next()) {
				stgrp[i]=new StudentGrpData();
				stgrp[i].info=rs.getString("info");
				stgrp[i].code=rs.getString("code");
				stgrp[i].strength=Integer.parseInt(rs.getString("strength"));
				stgrp[i].course[0]=rs.getString("sub1");
				stgrp[i].course[1]=rs.getString("sub2");
				stgrp[i].course[2]=rs.getString("sub3");
				stgrp[i].course[3]=rs.getString("sub4");
				stgrp[i].course[4]=rs.getString("sub5");

				for(j=0;j<5;j++) {
					for(k=0;k<nocourse;k++) {
						if(course[k].code.equals(stgrp[i].course[j]))
							course[k].stgrp[course[k].nostgrp++]=stgrp[i].code;
					}        	        				
				}
				i++;
			}
			nostgrp=i;

			rs=st.executeQuery("SELECT * FROM instructors");
			i=0;
			while(rs.next()) {
				ins[i]=new InstructorData();
				ins[i].name=rs.getString("tname");
				ins[i].code=rs.getString("tcode");
				ins[i].course[0]=rs.getString("tsub");
				for(k=0;k<nocourse;k++) {
					if(course[k].code.equals(ins[i].course[0]))
						course[k].ins[course[k].noins++]=ins[i].code;
				}
				i++;
			}
			noins=i;

			rs=st.executeQuery("SELECT * FROM classrooms");
			i=0;
			while(rs.next()) {
				rooms[i]=new ClassData();
				rooms[i].code=rs.getString("code");
				rooms[i].department=rs.getString("dep");
				rooms[i].strength=Integer.parseInt(rs.getString("strength"));
				i++;
			}
			noroom=i;

			cn.close();
		}

		catch (Exception e) {
		}	
	}

	int returnClassNo() {
		return(noroom);
	}
	int returnInstructorNo() {
		return(noins);
	}
	int returnStudentGrpNo() {
		return(nostgrp);
	}
	int returnCourseNo() {
		return(nocourse);
	}
}

class Gene {
	int[] g;
	Gene(int course, int stdgrp, int ins) {
		g=new int[Integer.toBinaryString(course).length()+Integer.toBinaryString(stdgrp).length()+Integer.toBinaryString(ins).length()];
	}
}

class Chromosome {
	Gene[] period;	
	Chromosome(int rooms) {
		period=new Gene[rooms*5*7]; //no. of working days=5 & no. of working hours each day=7(9:30 to 13:30 and 14:30 to 17:30)
	}

	double fitness(InputData input, int norooms, int nocourse, int nostgrp, int noins) {
		int k,i,j,room=-1,flag;
		double fitnessvalue=0,fit=0;
		int lcourse=Integer.toBinaryString(nocourse).length();
		int lstgrp=Integer.toBinaryString(nostgrp).length();
		int lins=Integer.toBinaryString(noins).length();
		String course="",stgrp="",ins="",tempst="",tempin="";
		int st;

		for(i=0;i<norooms*5*7;i++) {
			if(i%35==0)
				room++;

			course="";
			for(j=0;j<lcourse;j++)
				course=course+period[i].g[j];

			stgrp="";
			for(j=lcourse;j<lcourse+lstgrp;j++)
				stgrp=stgrp+period[i].g[j];

			ins="";
			for(j=lcourse+lstgrp;j<lcourse+lstgrp+lins;j++)
				ins=ins+period[i].g[j];

			st=Integer.valueOf(stgrp,2);

			if(input.rooms[room].strength>=input.stgrp[st].strength)
				fit++;

			//check for student grp repeat
			flag=1;
			for(j=i+35;j<norooms*5*7;j+=35) {
				tempst="";
				for(k=lcourse;k<lcourse+lstgrp;k++)
					tempst=tempst+period[j].g[k];
				if(tempst.equals(stgrp))
					flag=0;
			}
			for(j=i-35;j>=0;j-=35) {
				tempst="";
				for(k=lcourse;k<lcourse+lstgrp;k++)
					tempst=tempst+period[j].g[k];
				if(tempst.equals(stgrp))
					flag=0;
			}
			if(flag==1)
				fit++;

			//check for instructors repeat
			flag=1;
			for(j=i+35;j<norooms*5*7;j+=35) {
				tempin="";
				for(k=lcourse+lstgrp;k<lcourse+lstgrp+lins;k++)
					tempin=tempin+period[j].g[k];
				if(tempin.equals(ins)) {	
					flag=0;
					break;
				}
			}
			for(j=i-35;j>=0;j-=35) {
				tempin="";
				for(k=lcourse+lstgrp;k<lcourse+lstgrp+lins;k++)
					tempin=tempin+period[j].g[k];
				if(tempin.equals(ins)) {
					flag=0;
					break;
				}
			}
			if(flag==1)
				fit++;
		}

		fitnessvalue=fit/(norooms*35);
		return(fitnessvalue);
	}
}

class Table {
	String course;
	String ins;
	String room;
	Table(){
		course="";
		ins="";
		room="";
	}
}

class TimeTable {
	private Chromosome sol;
	static Table[][][] ttable;
	InputData input;
	int nostgrp,noclass,noins,nocourse;
	TimeTable(Chromosome solution, int stgrp1, int nclass, int ins, int course, InputData input1) {
		sol=solution;
		nostgrp=stgrp1;
		ttable=new Table[nostgrp][7][5];
		input=input1;
		int i,j,k;
		for(i=0;i<nostgrp;i++) {
			for(j=0;j<7;j++) {
				for(k=0;k<5;k++)
					ttable[i][j][k]=new Table();
			}
		}
		noclass=nclass;
		noins=ins;
		nocourse=course;
	}

	void findTable() {
		int lcourse=Integer.toBinaryString(nocourse).length();
		int lstgrp=Integer.toBinaryString(nostgrp).length();
		int lins=Integer.toBinaryString(noins).length();
		int i,j,k,cs,st,in,room=-1,d=-1,t=0;
		String course,stgrp,ins;

		for(i=0;i<noclass*35;i++) {
			if(i%35==0) {
				room++;
				d=-1;
			}
			if((i%7==0)) {
				t=0;
				d++;
			}	

			course="";
			for(j=0;j<lcourse;j++)
				course=course+sol.period[i].g[j];

			stgrp="";
			for(j=lcourse;j<lcourse+lstgrp;j++)
				stgrp=stgrp+sol.period[i].g[j];

			ins="";
			for(j=lcourse+lstgrp;j<lcourse+lstgrp+lins;j++)
				ins=ins+sol.period[i].g[j];
			
			st=Integer.valueOf(stgrp,2);

			cs=Integer.valueOf(course,2);

			in=Integer.valueOf(ins,2);

			ttable[st][t][d]=new Table();
			ttable[st][t][d].course=ttable[st][t][d].course+input.course[cs].code;
			ttable[st][t][d].ins=ttable[st][t][d].ins+input.ins[in].code;
			ttable[st][t][d].room=ttable[st][t][d].room+input.rooms[room].code;
			t++;
		}
		for(i=0;i<nostgrp;i++) {
			for(j=0;j<7;j++) {
				for(k=0;k<5;k++)
					if(ttable[i][j][k].course.equals("")) {
						ttable[i][j][k].course="--";
						ttable[i][j][k].room="--";
						ttable[i][j][k].ins="--";
					}
			}
		}
		PrintTable print=new PrintTable(ttable,nostgrp,input);
		print.print();
	}
}

public class Schedule {
	static Chromosome[] classes;
	static int noclass,nocourse,noins,nostgrp;
	static double[] fit;
	static InputData input;

	static void createpopulation() {
		int i,j,k,l,le,c,s,in,m=0,n;
		boolean flag;
		noclass=input.returnClassNo();
		nocourse=input.returnCourseNo();
		nostgrp=input.returnStudentGrpNo();
		noins=input.returnInstructorNo();
		Random r=new Random();
		classes=new Chromosome[10000];
		for(k=0;k<10000;k++) {
			classes[k]=new Chromosome(noclass);
			for(i=0;i<noclass*5*7;i++) {
				m=0;

				l=Integer.toBinaryString(nocourse).length();

				//course generation
				c=r.nextInt(nocourse);
				classes[k].period[i]=new Gene(nocourse, nostgrp, noins);
				String x=Integer.toBinaryString(c);
				le=x.length();
				if(l>le) {
					for(j=0;j<l-le;j++)
						classes[k].period[i].g[m++]=0;
				}
				for(j=0;j<le;j++) {
					classes[k].period[i].g[m++]=(x.charAt(j)-'0');
				}
				n=m;

				//student grp generation
				flag=false;
				while(!flag) {
					s=r.nextInt(nostgrp);
					for(j=0;j<input.course[c].nostgrp;j++) {
						if(input.course[c].stgrp[j].equals(input.stgrp[s].code)) {
							m=n;
							l=Integer.toBinaryString(nostgrp).length();
							x=Integer.toBinaryString(s);
							le=x.length();
							if(l>le) {
								for(j=0;j<l-le;j++)
									classes[k].period[i].g[m++]=0;
							}
							for(j=0;j<le;j++) {
								classes[k].period[i].g[m++]=x.charAt(j)-'0';
							}
							flag=true;
							break;
						}
					}
				}	
				n=m;	

				//instructor generation
				flag=false;
				while(!flag) {
					in=r.nextInt(noins);
					for(j=0;j<input.course[c].noins;j++) {
						if(input.course[c].ins[j].equals(input.ins[in].code)) {
							m=n;
							l=Integer.toBinaryString(noins).length();
							x=Integer.toBinaryString(in);
							le=x.length();
							if(l>le) {
								for(j=0;j<l-le;j++)
									classes[k].period[i].g[m++]=0;
							}
							for(j=0;j<le;j++) {
								classes[k].period[i].g[m++]=(x.charAt(j)-'0');
							}
							flag=true;
							break;
						}
					}
				}
				//Chromosome initialization finished
			}
		}
		//Chromosome initial population created
	}

	static void fitness(int size) {
		fit=new double[size];
		int i;
		for(i=0;i<size;i++) {
			fit[i]=classes[i].fitness(input, noclass, nocourse, nostgrp, noins);
		}
	}

	static int branchAndBound(int size) {
		int i,flag,l,j,k=0;
		double small=fit[0],high=fit[0];
		double[][] value=new double[1500][2];
		for(i=1000;i>=8;i/=5) {
			k=0;
			for(j=0;j<size;j++) {
				if((j%i==0)&&(j!=0)) {
					value[k][0]=small;
					value[k][1]=high;
					small=fit[j];
					high=fit[j];
					k++;
					continue;
				}
				if(small>fit[j])
					small=fit[j];
				if(high<fit[j])
					high=fit[j];
			}
			for(j=0;j<k;j++) {
				flag=0;
				for(l=0;l<k;l++) {
					if(l==j)
						continue;
					if(value[j][1]<value[l][0]) {
						flag=1;
						break;
					}
				}
				if(flag==1) {
					for(l=(j*i);l<size-i;l++)
						classes[l]=classes[l+i];
					size=size-i;
				}
			}
		}
		return(size);
	}

	//parent selection for crossover
	static int[] select(int size) {
		int[] select=new int[2];
		int[] rwheel=new int[100000];
		int i,j,k=0,percent;
		double fitsum=0;
		for(i=0;i<size;i++) {
			fitsum=fitsum+fit[i];
		}
		for(i=0;i<size;i++) {
			percent=(int)((((double)fit[i])/fitsum)*100000);
			for(j=k;j<k+percent;j++)
				rwheel[j]=i;
			k=k+percent;
		}
		Random r=new Random();
		//first parent
		select[0]=rwheel[r.nextInt(100000)];
		//second parent
		select[1]=rwheel[r.nextInt(100000)];

		return(select);
	}

	//single point crossover
	static void crossover(int[] parent,Chromosome son1,Chromosome son2) {
		Random n1=new Random();
		int r1,row1,col1,i,j;
		int len_course=Integer.toBinaryString(nocourse).length();
		int len_stgrp=Integer.toBinaryString(nostgrp).length();
		int len_ins=Integer.toBinaryString(noins).length();
		int len_gene=len_course+len_stgrp+len_ins;
		r1=n1.nextInt(len_gene*noclass*35);
		row1=r1/(len_gene);
		col1=r1%len_gene;

		//single point crossover
		for(i=0;i<row1;i++) {
			son1.period[i]=new Gene(nocourse, nostgrp, noins);
			son2.period[i]=new Gene(nocourse, nostgrp, noins);
			for(j=0;j<len_gene;j++) {
				son1.period[i].g[j]=classes[parent[1]].period[i].g[j];
				son2.period[i].g[j]=classes[parent[0]].period[i].g[j];
			}
		}
		for(j=0;j<col1;j++) {
			son1.period[row1]=new Gene(nocourse, nostgrp, noins);
			son2.period[row1]=new Gene(nocourse, nostgrp, noins);
			son1.period[row1].g[j]=classes[parent[1]].period[row1].g[j];
			son2.period[row1].g[j]=classes[parent[0]].period[row1].g[j];
		}
		for(j=col1;j<len_gene;j++) {
			son1.period[row1]=new Gene(nocourse,nostgrp,noins);
			son2.period[row1]=new Gene(nocourse,nostgrp,noins);
			son1.period[row1].g[j]=classes[parent[0]].period[row1].g[j];
			son2.period[row1].g[j]=classes[parent[1]].period[row1].g[j];
		}

		for(i=row1+1;i<noclass*35;i++) {
			son1.period[i]=new Gene(nocourse, nostgrp, noins);
			son2.period[i]=new Gene(nocourse, nostgrp, noins);

			for(j=0;j<len_gene;j++) {
				son1.period[i].g[j]=classes[parent[0]].period[i].g[j];
				son2.period[i].g[j]=classes[parent[1]].period[i].g[j];
			}
		}
	}

	static void mutation(Chromosome son) {
		int i;
		int len_course=Integer.toBinaryString(nocourse).length();
		int len_stgrp=Integer.toBinaryString(nostgrp).length();
		int len_ins=Integer.toBinaryString(noins).length();
		int len_gene=len_course+len_stgrp+len_ins;

		for(i=0;i<noclass*35;i++) {
			Random r=new Random();
			int pos=r.nextInt(len_gene*35*noclass);
			int row=pos/len_gene;
			int col=pos%len_gene;
			if(son.period[row].g[col]==0)
				son.period[row].g[col]=1;
			if(son.period[row].g[col]==1)
				son.period[row].g[col]=0;
		}
	} 

	Schedule() {
		int i=0,j,k=0,l,count=0,g=0;
		Chromosome temp;
		double tempfitt []=new double [5];
		double tempfit,maxfit=0;
		Chromosome[] newgen=new Chromosome[10000];
		Chromosome[] sonparent=new Chromosome[6]; //stores current parents, crossover sons and mutation sons
		double[] fsonparent=new double[6]; //stores corresponding fitness values
		double fit1,fit2,fitp1,fitp2;
		int size=10000;	//size of population
		int[] parent=new int[2];
		input=new InputData();
		input.takeInput();
		Chromosome maxchrome=new Chromosome(noclass);
		Chromosome tempchrome=new Chromosome(noclass);

		//chromosome population initialization
		createpopulation();

		//fitness initialization
		fitness(size);
		for(l=0;l<3;l++) {

			//branch and bound to reduce the size and improve the quality of population set
			size=branchAndBound(size);

			//fitness calculation
			fitness(size);

			for(i=0;i<size/2;i++) {
				//parent selection for crossover operation
				newgen[2*i]=new Chromosome(noclass);
				newgen[2*i+1]=new Chromosome(noclass);

				parent=select(size);
				fitp1=fit[parent[0]];
				fitp2=fit[parent[1]];

				sonparent[0]=classes[parent[0]];
				sonparent[1]=classes[parent[1]];
				fsonparent[0]=fitp1;
				fsonparent[1]=fitp2;

				//crossover
				Chromosome son1=new Chromosome(noclass);
				Chromosome son2=new Chromosome(noclass);
				crossover(parent,son1,son2);
				fit1=son1.fitness(input, noclass, nocourse, nostgrp, noins);
				fit2=son2.fitness(input, noclass, nocourse, nostgrp, noins);

				sonparent[2]=son1;
				sonparent[3]=son2;
				fsonparent[2]=fit1;
				fsonparent[3]=fit2;

				fit1=son1.fitness(input, noclass, nocourse, nostgrp, noins);
				fit2=son2.fitness(input, noclass, nocourse, nostgrp, noins);

				sonparent[4]=son1;
				sonparent[5]=son2;
				fsonparent[4]=fit1;
				fsonparent[5]=fit2;

				//sort sonparent on basis of fsonparent
				for(j=1;j<6;j++) {
					temp=sonparent[j];
					tempfit=fsonparent[j];
					for(k=j-1;k>=0;k--) {
						if(fsonparent[k]>tempfit) {
							sonparent[k+1]=sonparent[k];
							fsonparent[k+1]=fsonparent[k];
						}
						else 
							break;
					}
					sonparent[k+1]=temp;
					fsonparent[k+1]=tempfit;
				}
				newgen[2*i]=sonparent[5];
				newgen[2*i+1]=sonparent[4];

			}

			for(i=0;i<size;i++) {

				classes[i]=newgen[i];
				fit[i]=newgen[i].fitness(input, noclass, nocourse, nostgrp, noins);
			}
			tempfit=fit[0];
			for(i=1;i<size;i++)
				if(tempfit<fit[i]) {
					tempfit=fit[i];
					tempchrome=classes[i];
				}
			if(count%5==0)
				tempfitt[count%5]=tempfit;
			if(count%5==1)
				tempfitt[count%5]=tempfit;
			if(count%5==2)
				tempfitt[count%5]=tempfit;
			if(count%5==3)
				tempfitt[count%5]=tempfit;
			if(count%5==4)
				tempfitt[count%5]=tempfit;
			count++;
			if(tempfitt[0]==tempfitt[1] && tempfitt[0]==tempfitt[2] && tempfitt[0]==tempfitt[3] && tempfitt[0]==tempfitt[4] && count>4) {	
				for(g=0;g<size;g++)
					mutation(classes[g]);
			}
			if(maxfit<tempfit) {
				maxfit=tempfit;
				maxchrome=tempchrome;
			}
			if(((maxfit<=3.0)&&(maxfit>=2.7))||(size<=1000))
				break;
		}

		TimeTable table1 = new TimeTable(maxchrome, nostgrp, noclass, noins, nocourse, input);
		table1.findTable();
	}
}