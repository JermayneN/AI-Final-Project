package pacman.controllers;

import java.util.Random;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.controllers.Controller;
import java.util.ArrayList;
import pacman.controllers.Controller;


import static pacman.game.Constants.*;

public class ChaseMentality extends Controller<MOVE> 
{

	private Random rnd=new Random();
	private MOVE[] allMoves=MOVE.values();
	
	
	public MOVE getMove(Game game,long timeDue)
	{
		
		int current=game.getPacmanCurrentNodeIndex();
		
		int minDistance=Integer.MAX_VALUE;
		GHOST minGhost=null;		
		
		//go after the pills and power pills
		int[] pills=game.getPillIndices();
		int[] powerPills=game.getPowerPillIndices();		
		
		ArrayList<Integer> targets=new ArrayList<Integer>();
		
		for(int i=0;i<pills.length;i++)					//finds the pills available			
			if(game.isPillStillAvailable(i))
				targets.add(pills[i]);
		
		for(int i=0;i<powerPills.length;i++)			//finds the power pills available
			if(game.isPowerPillStillAvailable(i))
				targets.add(powerPills[i]);				
		
		int[] targetsArray=new int[targets.size()];		//convert from ArrayList to array
		
		for(int i=0;i<targetsArray.length;i++)
			targetsArray[i]=targets.get(i);
		

		//for chasing ghost when they blue 
		for(GHOST ghost : GHOST.values())
			if(game.getGhostEdibleTime(ghost)>0)
			{
				int distance=game.getShortestPathDistance(current,game.getGhostCurrentNodeIndex(ghost));
				
				if(distance<minDistance)
				{
					minDistance=distance;
					minGhost=ghost;
				}
			}
		
		
		//return the next direction once the closest target has been identified
		//if(minGhost!=null)
			//return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),game.getClosestNodeIndexFromNodeIndex(current,targetsArray,DM.PATH),DM.PATH);
			//didnt work as well
		
		if(minGhost!=null)	//we found an edible ghost
			return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(minGhost),DM.PATH);
		 
		return allMoves[rnd.nextInt(allMoves.length)]; 
		
	}
}
