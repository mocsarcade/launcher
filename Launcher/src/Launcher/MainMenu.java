package Launcher;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import OpenFunctions.GameFunction;
import openMenus.ButtonInfo;
import openMenus.EmptyItem;
import openMenus.MenuButton;
import openMenus.GameButton;

public class MainMenu {// extends JPanel {
	
	//public static int activeX;
	//public static int activeY;
	public static MenuButton leftButton;
	public static MenuButton centerButton;
	public static MenuButton rightButton;
	public static final int LEFT_COL = 0;
	public static final int MIDDLE_COL = 1;
	public static final int RIGHT_COL = 2;
	
	public static final int IMAGE_SIZE = 500;

   //public MainMenu(int row, int col) {
   public static void createMenu(JPanel games) throws IOException {
	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      //Initialize MenuItems
	  
	  //Make GameInfo. Right now it holds a useless function object. Later we will create OpenGame objects
	  //for what type of game this object is
      List<ButtonInfo> gameInfo = new ArrayList<ButtonInfo>();
	  //Load all games and place in a LIST of GameFunction objects
	  Scanner in = new Scanner(new File("games/allGames.txt"));
	  while(in.hasNext()) {
		  String gameName = in.next().trim();
		  gameInfo.add(new ButtonInfo(
				  new ImageIcon(ImageIO.read(new File("games/" + gameName + "/image.jpg"))),
				  new GameFunction(gameName)));
	  }
	  in.close();
	  //Iterator<ButtonInfo> infoIter = gameInfo.iterator();
	  
	  int active = 0; int cols = 2;
	  leftButton = new GameButton(1, LEFT_COL, screenSize.width/cols - 10, 250, GetLeft(gameInfo, active));
	  centerButton = new GameButton(1, MIDDLE_COL, screenSize.width/cols - 10, 250, GetCenter(gameInfo, active));
	  rightButton = new GameButton(1, RIGHT_COL, screenSize.width/cols - 10, 250, GetRight(gameInfo, active));
	  
	  games.setLayout(null);
      games.setBounds(0, 100, screenSize.width, screenSize.height-100);
	  games.add(leftButton);
	  leftButton.setBounds(250+125-(IMAGE_SIZE/2) - (IMAGE_SIZE+100), (games.getHeight()/2)-IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE);
	  games.add(centerButton);
	  centerButton.setBounds(250+125-(IMAGE_SIZE/2), (games.getHeight()/2)-IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE); //250 is the width of the header buttons
	  games.add(rightButton);
	  rightButton.setBounds(250+125-(IMAGE_SIZE/2) + (IMAGE_SIZE+100), (games.getHeight()/2)-IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE);
	  
      //Games Panel
	  /*
	  int rows=2;
	  int cols=(int) screenSize.width/255;
	  //games.setLayout(new GridLayout(rows,cols,(screenSize.width-cols*250)/(cols-1), (int) (screenSize.getSize().getHeight()-250*rows)/(rows-1) ));
	  games.setLayout(new GridLayout(rows,cols,5, (int) (screenSize.height*0.9-500)/6 ));
	  //games.setBorder(new EmptyBorder( (int) (screenSize.height*0.9-500)/6, 0, (int) (screenSize.height*0.9-500)/6, 0));
      for (int i = 1; i <rows+1; i++) { //Start at 1 because row 0 is headerMenus
         for (int j = 0; j < cols; j++) {
        	 if(infoIter.hasNext()) {
            	 games.add(new MenuButton(i, j, screenSize.width/cols - 10, 250, infoIter.next()));
        	 } else {
	    		  //games.add(new MenuButton(i, j, screenSize.width/cols - 10, 250, new ButtonInfo(new ImageIcon(ImageIO.read(new File("images/nothing.jpg"))), new Function())));
	    		  games.add(new EmptyItem(i, j, screenSize.width/cols - 10, 250, new ButtonInfo()));
        	 }
         }
      }*/

      //games.setBounds(0, screenSize.height-250*2-100, screenSize.width, 250*2+100);
      //games.setBounds(0, screenSize.height-250*2-100, 250*Math.min(cols, counter), 250*2+100);
      //return games;
      
   }
   
   public static void UpdateGames(MenuButton newCenter) {
	   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	   int activeX = 250+125-(IMAGE_SIZE/2);
	   while(newCenter.getX() != activeX) {
		   if(newCenter.getX() > activeX)
			   newCenter.setBounds(newCenter.getX()-1, newCenter.getY(), newCenter.getWidth(), newCenter.getHeight());
		   if(newCenter.getX() < activeX)
			   newCenter.setBounds(newCenter.getX()+1, newCenter.getY(), newCenter.getWidth(), newCenter.getHeight());
		   try {
			   java.util.concurrent.TimeUnit.MICROSECONDS.sleep(1);
		   }
		   catch(InterruptedException ex)
		   {
		   }
		   //Repainting Not Working. Perhaps make this background process?
		   GUIMain.contentMenu.revalidate();
	   }
   }
   
   public static ButtonInfo GetLeft(List<ButtonInfo> gameInfo, int active) {
	   //If Left button is out of bounds, loop around list
	   if(active-1 < 0) {
		   return gameInfo.get(active - 1 + gameInfo.size());
	   }
	   //If Left button is out of bounds, loop around list
	   else if(active-1 >= gameInfo.size()) {
		   return gameInfo.get(active - 1 - gameInfo.size());
	   }
	   else {
		   return gameInfo.get(active - 1);
	   }
   }
   
   public static ButtonInfo GetCenter(List<ButtonInfo> gameInfo, int active) {
	   //If Center button is out of bounds, loop around list
	   if(active < 0) {
		   return gameInfo.get(active + gameInfo.size());
	   }
	   //If Left button is out of bounds, loop around list
	   else if(active >= gameInfo.size()) {
		   return gameInfo.get(active - gameInfo.size());
	   }
	   else {
		   return gameInfo.get(active);
	   }
   }
   
   public static ButtonInfo GetRight(List<ButtonInfo> gameInfo, int active) {
	   //If Center button is out of bounds, loop around list
	   if(active + 1 < 0) {
		   return gameInfo.get(active + 1 + gameInfo.size());
	   }
	   //If Left button is out of bounds, loop around list
	   else if(active + 1 >= gameInfo.size()) {
		   return gameInfo.get(active + 1 - gameInfo.size());
	   }
	   else {
		   return gameInfo.get(active + 1);
	   }
   }
}
