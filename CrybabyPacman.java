package pacman.controllers;

import java.util.Random;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.controllers.Controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import pacman.controllers.Controller;


import static pacman.game.Constants.*;


public class CrybabyPacman extends Controller<MOVE> 
{

	private Random rnd=new Random();
	private MOVE[] allMoves=MOVE.values();
	private static final int MIN_DISTANCE=40;	//10 //60 //20 //if a ghost is this close, run away
	
	
	
	public MOVE getMove(Game game,long timeDue)
	{
		int location=game.getPacmanCurrentNodeIndex();
		
		//run away base code
		for(GHOST ghost : GHOST.values())
			if(game.getGhostEdibleTime(ghost)==0) //if not edible 
				if(game.getShortestPathDistance(location,game.getGhostCurrentNodeIndex(ghost))<MIN_DISTANCE)
					return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghost),DM.PATH);
		
		return allMoves[rnd.nextInt(allMoves.length)];
	}
}
