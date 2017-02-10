package javagames.util;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RelativeMouseInput implements MouseListener, MouseMotionListener, MouseWheelListener{
	private static final int BUTTON_COUNT = 3;
	private Point mousePos;
	private Point currentPos;
	private boolean[] mouse;
	private int[] polled;
	private int notches;
	private int polledNotches;
	private int dx, dy;
	private Robot robot;
	private Component component;
	private boolean relative;
	public RelativeMouseInput(Component component){
		this.component = component;
		try{
			robot = new Robot();
		}catch(Exception e){
			e.printStackTrace();
		}
		mousePos = new Point(0, 0);
		currentPos = new Point(0, 0);
		mouse = new boolean[BUTTON_COUNT];
		polled = new int[BUTTON_COUNT];
	}
	public synchronized void poll(){
		if(isRelative()){
			mousePos = new Point(dx, dy);
		}else{
			mousePos = new Point(currentPos);
		}
		dx = dy = 0;
		polledNotches = notches;
		notches = 0;
		for(int i = 0; i < mouse.length; ++i){
			if(mouse[i]){
				polled[i]++;
			}else{
				polled[i] = 0;
			}
		}
	}
	public boolean isRelative(){
		return relative;
	}
	public void setRelative(boolean relative){
		this.relative = relative;
		if(relative){
			centerMouse();
		}
	}
	public Point getPosition(){
		return mousePos;
	}
	public int getNotches(){
		return polledNotches;
	}
	public boolean buttonDown(int button){
		return polled[button - 1] > 0;
	}
	public boolean buttonDownOnce(int button){
		return polled[button - 1] == 1;
	}
	@Override
	public synchronized void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		notches += e.getWheelRotation();
	}

	@Override
	public synchronized void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseMoved(e);
	}

	@Override
	public synchronized void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(isRelative()){
			Point p = e.getPoint();
			Point center = getComponentCenter();
			dx += p.x - center.x;
			dy += p.y - center.y;
			centerMouse();
		}else{
			currentPos = e.getPoint();
		}
	}

	@Override
	public synchronized void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int button = e.getButton() - 1;
		if(button >= 0 && button < mouse.length){
			mouse[button] = true;
		}
	}

	@Override
	public synchronized void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		int button = e.getButton() - 1;
		if(button >= 0 && button < mouse.length){
			mouse[button] = false;
		}
	}

	@Override
	public synchronized void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseMoved(e);
	}

	@Override
	public synchronized void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseMoved(e);
	}
	private Point getComponentCenter(){
		int w = component.getWidth();
		int h = component.getHeight();
		return new Point(w/2, h/2);
	}
	private void centerMouse(){
		if(robot != null && component.isShowing()){
			Point center = getComponentCenter();
			SwingUtilities.convertPointToScreen(center, component);
			robot.mouseMove(center.x, center.y);
		}
	}
}
