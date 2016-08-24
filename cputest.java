import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class cputest extends Thread {

	private static Scanner s;
	static int count=0;
	static double total=0,avg=0;
	static int cores = Runtime.getRuntime().availableProcessors();
	long f=1,d=2,e=3;
	static long start,end,loop,a;
	static int time,op,not;
	static DataOutputStream w;
	static int seconds = 0;
	static Double dat[]=new Double[600];
	private static PrintWriter out;
	static Timer timer;
	public static void main(String ar[]) throws InterruptedException,Exception
	{	
		s = new Scanner(System.in);
		cputest t=new cputest();
		System.out.println("Operation\n1.Gigaflops\n2.IOPS\n\nNumber of Threads\n1. 1\n2. 2\n3. 3\n4. 4\n\nTime duration\n1.Short time (less than 1 mins)\n2.Long Time (For 10 minutes)\n\nEnter 5 to exit");
		while(true)
		{	
		System.out.println("\nEnter Choice");
		op=s.nextInt();
		if(op==5)
			System.exit(0);
		else if(op==2)
			op=2;
		else
			op=1;
		not=s.nextInt();
		if(not>4)
			not=4;
		time=s.nextInt();
		if(time==2){
			timer = new Timer(true);
			toiopsarray write = new toiopsarray();
			timer.scheduleAtFixedRate(write, 1000, 1000);
			loop=600000000000l;
		}
		else
			loop=1000000000l;
		
		for(int i=1;i<=not;i++)
		{
			Thread t1=new Thread(t);
			t1.start();
			t1.join();
		}
		printavg(not);
		}
	}
	public void run()
	{	
		start=System.currentTimeMillis();
		for(a=1l;a<=loop;a++)
		{	
			f=f*a;
			if(time==2)System.out.print("");
			if(seconds>=600){
				timer.cancel();
				System.out.println("IOPS data.csv created for 600 seconds");
				try {
					data();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		end=System.currentTimeMillis();
		total=(end-start)/60000.0;
		avg=avg+total;
	}
	static void printavg(double i)
	{
		if(i>1)
		System.out.println(avg+" Total time in minutes");
		avg=avg/i;
		System.out.println(avg+" Average time/thread in minutes");
		double x = loop*1.0/(avg*(Math.pow(10,9)));
		if(op==1)
		System.out.println(x+" GFlops/seconds");
		if(op==2)
		System.out.println((int)x+" IOPS");
		avg=0;
	}
	static void data() throws FileNotFoundException
	{	
		File file = new File("IOPS data.csv");
		if(file.exists()){
			file.delete();}
		FileOutputStream fileos = new FileOutputStream(file);
		out = new PrintWriter(fileos);
		for(int i =0;i<dat.length;i++){
			out.println(i+1+","+dat[i]);
		}
		out.close();
		System.exit(0);
	}
	static class toiopsarray extends TimerTask
	{double z=0;
		public void run() {
				dat[seconds] = (double) a-z;
				z=a;
				seconds++;
		}
	}
}

