package org.angradb.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class MainCLI {

	private Options options; 
	private Socket client; 
	
	private MainCLI() {
		options = new Options();
		options.addOption(new Option("h", true, "host"));
		options.addOption(new Option("p", true, "port"));
	}
	
	private void connect(String host, int port) throws Exception {
		System.out.print("Trying to connect to " + host + "....");
		
//		for(int i = 0; i < 5; i++) {
//			wait(1000);
//			System.out.print(".");
//		}
		 client = new Socket(host, port);
		 System.out.println(" done! ");
	}
	
	public void processaComandos() throws Exception {
		Scanner sc = new Scanner(System.in);

		while(true) {
			System.out.println("AngraDB > ");
			
			StringBuilder buffer = new StringBuilder();
			
			String str = sc.nextLine();
			buffer.append(str);
			
			while(!str.endsWith(";")) {
				str = sc.nextLine();
				buffer.append(str);
			}
			buffer.setLength(buffer.length()-1);
		
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			InputStreamReader in = new InputStreamReader(client.getInputStream());
			BufferedReader reader = new BufferedReader(in);
			
			out.println(buffer.toString());
			
			System.out.println(reader.readLine());
			System.out.println("Done.");
		}
	}
	
	public static void main(String args[]) {
		MainCLI main = new MainCLI();
		try {
			CommandLineParser parser = new BasicParser();
			CommandLine cmd = parser.parse(main.options, args);
			
			String host = "127.0.0.1";
			int port = 1234;
			
			if(cmd.hasOption("h") && cmd.hasOption("p")) {
				host = cmd.getOptionValue("h");
				port = Integer.parseInt(cmd.getOptionValue("p"));
			}
//			else { 
//				System.out.println("Error. Inform the host and port");
//				System.exit(1);
//			}
			
			main.connect(host, port);
			
			main.processaComandos();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
