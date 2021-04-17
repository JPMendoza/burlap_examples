package edu.brown.cs.burlap.tutorials;

import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.planning.Planner;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.domain.singleagent.blockdude.BlockDude;
import burlap.domain.singleagent.blockdude.BlockDudeVisualizer;
import burlap.domain.singleagent.blockdude.state.BlockDudeAgent;
import burlap.domain.singleagent.blockdude.state.BlockDudeCell;
import burlap.domain.singleagent.blockdude.state.BlockDudeMap;
import burlap.domain.singleagent.blockdude.state.BlockDudeState;
import burlap.domain.singleagent.gridworld.GridWorldDomain;
import burlap.domain.singleagent.gridworld.GridWorldVisualizer;
import burlap.domain.singleagent.gridworld.state.GridAgent;
import burlap.domain.singleagent.gridworld.state.GridLocation;
import burlap.domain.singleagent.gridworld.state.GridWorldState;
import burlap.mdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.shell.visual.VisualExplorer;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import burlap.visualizer.Visualizer;

import java.util.ArrayList;


/**
 * @author James MacGlashan.
 */
public class BlockDudeHelloWorld {

	public static void main(String[] args) {

		OOSADomain domain;
		TerminalFunction tf;
		StateConditionTest goalCondition;
		HashableStateFactory hashingFactory = new SimpleHashableStateFactory();
		State initialState;


		BlockDude bd = new BlockDude();
		BlockDudeAgent ag = new BlockDudeAgent(0,0,1,false);
		BlockDudeMap map = new BlockDudeMap( 20,  1);
		BlockDudeCell exit = new BlockDudeCell(15,1, "exit", "exit");

		ArrayList<BlockDudeCell> blocks = new ArrayList<>();
		BlockDudeCell b1 = new BlockDudeCell(3,1, "block", "b1");
		BlockDudeCell b2 = new BlockDudeCell(4,1, BlockDude.CLASS_BLOCK, "b2");
		BlockDudeCell b3 = new BlockDudeCell(5,1, BlockDude.CLASS_BLOCK, "b3");
		BlockDudeCell b4 = new BlockDudeCell(6,1, BlockDude.CLASS_BLOCK, "b4");
		BlockDudeCell b5 = new BlockDudeCell(7,1, BlockDude.CLASS_BLOCK, "b5");
		//blocks.add(b1);
		//blocks.add(b2);
		//blocks.add(b3);
		//blocks.add(b4);
		//blocks.add(b5);


		domain = bd.generateDomain(); //generate the grid world domain

		//setup initial state
		State s = new BlockDudeState(ag,map,exit,blocks);
		initialState = s;

		// test

		Planner planner = new ValueIteration(domain, 0.99, hashingFactory, 0.001, 500);
		long start = System.currentTimeMillis();
		Policy p = planner.planFromState(initialState);
		long end = System.currentTimeMillis();
		System.out.println("Value Iteration: " + (end - start) / 1000.0);

		// test


		//create visualizer and explorer
		Visualizer v = BlockDudeVisualizer.getVisualizer(bd.getMaxx(), bd.getMaxy());
		VisualExplorer exp = new VisualExplorer(domain, v, s);

		//set control keys to use w-s-a-d
		exp.addKeyAction("w", BlockDude.ACTION_UP, "");
		exp.addKeyAction("a", BlockDude.ACTION_WEST, "");
		exp.addKeyAction("d", BlockDude.ACTION_EAST, "");
		exp.addKeyAction("s", BlockDude.ACTION_PICKUP, "");
		exp.addKeyAction("x", BlockDude.ACTION_PUT_DOWN, "");


		exp.initGUI();

	}

}
