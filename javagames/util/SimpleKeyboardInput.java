package javagames.util;
import java.awt.event.*;

public class SimpleKeyboardInput implements KeyListener{
	private boolean[] keys;
	public SimpleKeyboardInput(){
		keys = new boolean[256];
	}
	public synchronized boolean keyDown(int keyCode){
		return keys[keyCode];
	}
	@Override
	public synchronized void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		if(keyCode >= 0 && keyCode < keys.length){
			keys[keyCode] = true;
		}
	}

	@Override
	public synchronized void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		if(keyCode >= 0 && keyCode <keys.length){
			keys[keyCode] = false;
		}
	}

}
