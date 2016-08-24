import java.io.*;
import java.util.Random;

public class disktest extends Thread {
	static RandomAccessFile radf;
	static File seqf;
	static BufferedWriter wseqfile,wrandfile;
	static BufferedReader rin,rseqfile,rrandfile;
	static char c;
	static int rw,sr,size, not;
	static long start,end;
	static double avg=0;
	Random r = new Random();
	static String s="1234567890qwertyuiopasd fghjklzxcvbn m[];',.{}|:<>?!@#$ %^&*()_+QWERTYUIO PASDFGHJ KLZXCVBNM";
	public static void main(String ar[]) throws Exception
	{	
		radf=new RandomAccessFile("radfile.txt","rw");
		rin=new BufferedReader(new InputStreamReader(System.in));
		wseqfile=new BufferedWriter(new FileWriter("seqfile.txt",true));
		rseqfile= new BufferedReader(new FileReader("seqfile.txt"));
		wrandfile=new BufferedWriter(new FileWriter("radfile.txt",true));
		rrandfile= new BufferedReader(new FileReader("radfile.txt"));
		System.out.println("Enter Any Combination \nOperation\n1.Write\n2.Read\n\nFile Access\n1.Sequential Access\n2.Random Access"
				+ "\n\nBuffer Size\n1. 1 Byte\n2. 1 Kbyte\n3. 1 Mbyte\n\nNumber of Threads\n1. 1\n2. 2\n\nOr 5 to exit");
		rw=Integer.parseInt(rin.readLine());
		if (rw==5)
			System.exit(0);
		sr=Integer.parseInt(rin.readLine());
		size=Integer.parseInt(rin.readLine());
		not=Integer.parseInt(rin.readLine());
		if(size==1)
			size=1;
		else if(size==2)
			size=1024;
		else
			size=1024*1024;
		
		for(int i=1;i<=not;i++)
		{
			Thread t=new Thread(new disktest());
			t.start();
			t.join();
		}
		printavg(not);
	}
	public void run()
	{
	try
	{
	start=System.currentTimeMillis();
	if(rw==1 && sr==1){
		for(long l=1;l<=size;l++){
		wseqfile.write(s.charAt(r.nextInt(s.length())));
		}
	}
	if(rw==1 && sr==2){
		for(long l=1;l<=size;l++){
		wrandfile.write(s.charAt(r.nextInt(s.length())));
	}
	}
	if(rw==2 && sr==1){
		for(long l=1;l<=size;l++){
			rseqfile.read();
		}
	}
	if(rw==2 && sr==2){
		for(long l=1;l<=size;l++){
			rrandfile.read();
		}
	}
	rrandfile.close();
	rseqfile.close();
	wrandfile.close();
	wseqfile.close();
	}
	catch(Exception e){}
	end=System.currentTimeMillis();
	avg=avg+(end-start);
	}
	static void printavg(int i)
	{	avg=avg/i;
		System.out.println(avg+" milliseconds");
		avg=avg/1000.0;
		System.out.println(size/(avg*1024*1024) +" MB/sec\n");
	}
}	