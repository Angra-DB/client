package org.angradb.client;

import java.io.IOException;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
public class Main {

	public static void main(String args[]) {
		try {
			Terminal terminal = (new DefaultTerminalFactory()).createTerminal();
			
			Screen screen = new TerminalScreen(terminal);
	        screen.startScreen();
	        
	        Panel mainPanel = new Panel();
	        mainPanel.setLayoutManager(new GridLayout(2));
	        
	        final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
	        
	        mainPanel.addComponent(new Button("Save", new Runnable() {
				@Override
				public void run() {
					String doc = new TextInputDialogBuilder().setTitle("Document editor").setTextBoxSize(new TerminalSize(35, 5))
					        .build()
					        .showDialog(textGUI);
					
					System.out.println(doc);
				}
			}));
	        
	        mainPanel.addComponent(new Button("Lookup"));
	        mainPanel.addComponent(new Button("Update"));
	        mainPanel.addComponent(new Button("Delete"));
	        
	        BasicWindow window = new BasicWindow();
	        window.setComponent(mainPanel);

	        // Create gui and start gui
	        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
	        gui.addWindowAndWait(window);
	        
		}
		catch(IOException e) {
			e.printStackTrace();
			
		}

	}
}