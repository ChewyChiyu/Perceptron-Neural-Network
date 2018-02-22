package com.chewychiyu.perceptrontest;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.chewychiyu.layeredperceptron.Multi_Perceptron;

@SuppressWarnings("serial")
public class NetworkTest extends JPanel{
	
	Thread network_test_;
	Runnable network_loop_;
	
	Turtle turtle_;
	
	public static void main(String[] args){
		new NetworkTest();
	}
	
	void panel(){
		JFrame frame_ = new JFrame("Network test");
		frame_.setPreferredSize(new Dimension(800,800));
		System.setProperty("sun.java2d.opengl", "true");
		frame_.add(this);
		this.setBackground(Color.BLACK);
		frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		frame_.pack();
		frame_.setResizable(false);
		frame_.setVisible(true);
	}
	
	NetworkTest(){	
		panel();
		Multi_Perceptron _network = new Multi_Perceptron(1,10,1);
		turtle_ = new Turtle(_network);
		network_loop_ = () -> loop();
		network_test_ = new Thread(network_loop_);
		network_test_.start();
	}
	
	void loop(){
		while(true){
		try{ 
		turtle_.move();
		turtle_.check_reset();
		repaint();
		Thread.sleep(10);
		}catch(Exception e) { }
		}
	}
	
	public void paintComponent(Graphics g_){
		super.paintComponent(g_);
		g_.setColor(Color.white);
		g_.fillRect(Math.round(turtle_.x_), (int)turtle_.y_, 50, 50);
		
		g_.fillRect(turtle_.target_x_, 0, 10, 10); //seek
		
	}
	
	
}

class Turtle{
	
	public float dx_ , dy_;
	public float x_,y_;
		
	public int target_x_ = 400;
	
	public Multi_Perceptron brain_;
	
	public Turtle(Multi_Perceptron _brain){
		dx_ = (float) ((Math.random()<.5)?Math.random()*7:-Math.random()*7);
		dy_ = -20f;
		brain_ = _brain;
		reset_position();
	}
	
	public void reset_position(){
		x_ = 350f;
		y_ = 800;
	}
	
	public void move(){
		x_+=dx_;
		y_+=dy_;
	}
	
	public void check_reset(){
		if(y_<0){
			//calculate distance from goal / center of screen
			float _distance = x_ - target_x_;
			//train
			float[] _input = new float[]{_distance};
			float[] _target = new float[]{(_distance>0)?1:0}; // we want it to be in the center
						
			System.out.println(_distance);
			//1 if distance is too large, 0  if distance is too small, 1
			brain_.train(_input, _target);
			
			float[] _delta_x = brain_.predict(_input); //finding what we should change dx
			
			System.out.println("prediction: " + _delta_x[0]);
			System.out.println("dx : " + dx_);
			System.out.println(_delta_x[0] > .5);
			if(_delta_x[0] > .5){ //distance too big positive
				dx_ -= .5f;
			}else{ //distance too big negative
				dx_ += .5f;
			}
			
			
			//checking to see if move target_seek
			if(Math.abs(_distance) < 10){ //10 pixel buffer
				target_x_ = 100 + (int)(Math.random()*600);
			}
			//reseting position
			reset_position();
		}
	}
}

