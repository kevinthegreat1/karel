package acm.program;

import acm.graphics.GCanvas;
import acm.graphics.GCanvasInterface;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.EventListener;

public interface GraphicsProgramInterface extends ProgramInterface, GCanvasInterface, MouseListener, MouseMotionListener {
	public void addKeyListeners();
	public void addKeyListeners(KeyListener listener);
	public void addMouseListeners();
	public void addMouseListeners(EventListener listener);
	public void clearCanvas();
	public double getCanvasHeight();
	public Dimension getCanvasSize();
	public double getCanvasWidth();
	public GCanvas getGCanvas();
	public void removeAllComponents();
	public void setCanvasHeight(double height);
	public void setCanvasSize(double width, double height);
	public void setCanvasWidth(double width);
	public void setSize(double width, double height);
	public void setWindowSize(double width, double height);
	public void waitForClick();
}
