/*
 * @version 2017/05/06
 * - showSaveDialog prompt to overwrite if file already exists 
 * @version 2017/04/30
 * - made showDiffDialog default to expected-output directory
 * @version 2017/04/26
 * - added getElementAt(double...), hasElementAt
 * - added remove(x, y), removeAll(x, y)
 * - added setShowPixelInfo, setShowPixelGrid
 * @version 2017/04/25
 * - added showSaveDialog to save canvas graphics to an image file
 * @version 2016/10/21
 * - added drawPolarLine
 * @version 2016/10/16
 * - added buffered image to get/set pixels
 * @version 2016/05/05
 * - restored from original eroberts source code
 * - alphabetized methods
 * - implements Iterable of GObject
 */

/*
 * @(#)GCanvas.java   1.99.1 08/12/08
 */

// ************************************************************************
// * Copyright (c) 2008 by the Association for Computing Machinery        *
// *                                                                      *
// * The Java Task Force seeks to impose few restrictions on the use of   *
// * these packages so that users have as much freedom as possible to     *
// * use this software in constructive ways and can make the benefits of  *
// * that work available to others.  In view of the legal complexities    *
// * of software development, however, it is essential for the ACM to     *
// * maintain its copyright to guard against attempts by others to        *
// * claim ownership rights.  The full text of the JTF Software License   *
// * is available at the following URL:                                   *
// *                                                                      *
// *          http://www.acm.org/jtf/jtf-software-license.pdf             *
// *                                                                      *
// ************************************************************************

// REVISION HISTORY
//
// -- V2.0 --
// Bug fix 27-Jul-06 (ESR, JTFBug 2006-001, reported by Chris Nevison)
//   1. Fixed implementation of enabledList.
//
// Bug fix 26-Apr-07 (ESR, JTFBug 2007-005, reported by Leland Beck)
//   1. Removed cross-file reference to GMouseEvent.
//
// Code cleanup 28-May-07 (ESR)
//   1. Added generic type tags.
//   2. Substituted GObjectList for ArrayList.
//   3. Removed warnings about use of Iterator.
//
// Feature enhancement 26-May-08 (ESR)
//   1. Added support for serialization.

package acm.graphics;

import acm.util.MediaTools;
import stanford.cs106.diff.DiffImage;
import stanford.cs106.gui.GuiUtils;
import stanford.cs106.io.IORuntimeException;
import stanford.cs106.io.IOUtils;
import stanford.spl.GBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

/* Class: GCanvas */
/**
 * The <code>GCanvas</code> class is a lightweight component that also
 * serves as a container for graphical objects.  As such, this class
 * provides the link between graphical objects and the window system.
 * Conceptually, the <code>GCanvas</code> provides a background canvas
 * to which other graphical objects can be added.
 */
public class GCanvas extends JComponent
		implements GCanvasInterface, GContainer, Iterable<GObject> {
	private static final long serialVersionUID = 0L;
	private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
	
	// color and font used to display mouse info
	private static final Color PIXEL_GRID_COLOR = Color.GRAY;
	private static final int PIXEL_GRID_INCREMENT = 10;
	private static final Color MOUSE_INFO_COLOR = Color.DARK_GRAY;
	private static final Font MOUSE_INFO_FONT = new JLabel("^_^").getFont();

	private GCanvasListener gCanvasListener;
	private GObject lastObject;
	private GObject dragObject;
	private GObjectList contents;
	private BufferedImage bufferedImage = null;
	private BufferedImage bufferedImage2 = null;   // used only if showPixelInfo is true
	private Graphics2D osg = null;
	private boolean antialias;
	private boolean autoRepaint;
	private boolean nativeArcFlag;
	private boolean showPixelGrid;
	private boolean showPixelInfo;
	private Point lastMousePoint = null;
	
	/**
	 * Creates a new <code>GCanvas</code> that contains no objects.
	 *
	 * @usage GCanvas gc = new GCanvas();
	 */
	public GCanvas() {
		contents = new GObjectList(this);
		bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);   // will be resized
		osg = (Graphics2D) bufferedImage.getGraphics();
		setAntiAliasing(GObject.isAntiAliasing());
		
		gCanvasListener = new GCanvasListener(this);
		
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		setOpaque(true);
		setAutoRepaintFlag(true);
		setNativeArcFlag(false);
		setShowPixelGrid(false);
		setShowPixelInfo(false);
		setLayout(null);
		
		addComponentListener(gCanvasListener);
		addMouseListener(gCanvasListener);
		addMouseMotionListener(gCanvasListener);
	}
	
	/**
	 * Adds the component to this canvas without changing its location.
	 * If the component has no size, its size is set to its preferred size.
	 * The return type is <code>Component</code> to match the method in
	 * the <code>Container</code> class, but the result is typically
	 * ignored.
	 *
	 * @usage gc.add(comp);
	 * @param comp The component to add
	 */
	public Component add(Component comp) {
		super.add(comp);
		Dimension size = comp.getSize();
		if (size.width == 0 || size.height == 0) {
			Dimension preferredSize = comp.getPreferredSize();
			if (size.width == 0) {
				size.width = preferredSize.width;
			}
			if (size.height == 0) {
				size.height = preferredSize.height;
			}
			comp.setSize(size);
		}
		return comp;
	}

	/**
	 * Adds the component to this canvas and sets its location
	 * to the point (<code>x</code>,&nbsp;<code>y</code>).
	 *
	 * @usage gc.add(comp, x, y);
	 * @param comp The component to add
	 * @param x The new x-coordinate for the object
	 * @param y The new y-coordinate for the object
	 */
	public final void add(Component comp, double x, double y) {
		comp.setLocation(GMath.round(x), GMath.round(y));
		add(comp);
	}

	/**
	 * Adds the component to this canvas and sets its location to the specified point.
	 *
	 * @usage gc.add(comp, pt);
	 * @param comp The component to add
	 * @param pt A <code>GPoint</code> object giving the coordinates of the point
	 */
	public final void add(Component comp, GPoint pt) {
		add(comp, pt.getX(), pt.getY());
	}

	/**
	 * Adds the graphical object to this canvas.
	 *
	 * @usage gc.add(gobj);
	 * @param gobj The graphical object to add
	 */
	public void add(GObject gobj) {
		contents.add(gobj);
		conditionalRepaint();
	}

	/**
	 * Adds the graphical object to this canvas and sets its location
	 * to the point (<code>x</code>,&nbsp;<code>y</code>).
	 *
	 * @usage gc.add(gobj, x, y);
	 * @param gobj The graphical object to add
	 * @param x The new x-coordinate for the object
	 * @param y The new y-coordinate for the object
	 */
	public final void add(GObject gobj, double x, double y) {
		gobj.setLocation(x, y);
		add(gobj);
	}

	/**
	 * Adds the graphical object to this canvas and sets its location to the specified point.
	 *
	 * @usage gc.add(gobj, pt);
	 * @param gobj The graphical object to add
	 * @param pt A <code>GPoint</code> object giving the coordinates of the point
	 */
	public final void add(GObject gobj, GPoint pt) {
		add(gobj, pt.getX(), pt.getY());
	}
	
	/**
	 * Removes all graphical objects from this canvas.
	 */
	public void clear() {
		// also clear out the buffered image
		clear(bufferedImage);
		clear(bufferedImage2);
		removeAll();
	}
	
	private void clear(BufferedImage img) {
		if (img != null) {
			int w = img.getWidth();
			int h = img.getHeight();
			Graphics g = img.getGraphics();
//			if (isOpaque()) {
//				g.setColor(getBackground());
//			} else {
				g.setColor(TRANSPARENT);
//			}
			g.fillRect(0, 0, w, h);
			g.setColor(getForeground());
		}
	}

	/**
	 * Repaints the canvas if auto-repaint is in effect.  This method is called only
	 * by the <a href="GObject.html#repaint()"><code>repaint</code></a> method in
	 * <code>GObject</code> and is not accessible outside the package.
	 *
	 * @usage gc.conditionalRepaint();
	 * @noshow
	 */
	protected void conditionalRepaint() {
		if (autoRepaint) {
			if (bufferedImage != null) {
				bufferedImage.flush();
			}
			repaint();
		}
	}
	
	/**
	 * Compares the pixels of this canvas to the image stored in the given file.
	 */
	public void diff(File file) {
		diff(file, /* ignoreWindowSize */ false);
	}
	
	/**
	 * Compares the pixels of this canvas to the image stored in the given file.
	 * If ignoreWindowSize is true, allows the two windows' sizes to differ as
	 * long as they don't have any different drawn shapes between them.
	 */
	public void diff(File file, boolean ignoreWindowSize) {
		BufferedImage windowImage = toImage();
		try {
			Image fileImage = MediaTools.loadImage(file);
			if (ignoreWindowSize) {
				// enlarge both to same (larger) size
				int w1 = windowImage.getWidth();
				int w2 = fileImage.getWidth(this);
				int wmax = Math.max(w1, w2);
				int h1 = windowImage.getHeight();
				int h2 = fileImage.getHeight(this);
				int hmax = Math.max(h1, h2);
				
				boolean opaque = this.isOpaque();
				Color background = this.getBackground();
				int backgroundColor = background.getRGB();
				
				BufferedImage bfileImage = new BufferedImage(wmax, hmax, BufferedImage.TYPE_INT_ARGB);
				if (opaque) {
					bfileImage.getGraphics().setColor(background);
					bfileImage.getGraphics().fillRect(0, 0, wmax, hmax);
				}
				bfileImage.getGraphics().drawImage(fileImage, 0, 0, this);
				
				if (w1 < wmax || h1 < hmax) {
					windowImage = this.toImage(wmax, hmax);
					
					for (int x = 0; x < wmax; x++) {
						for (int y = 0; y < hmax; y++) {
							if ((x >= w1 || y >= h1) && (x < w2 && y < h2)) {
								int rgb = bfileImage.getRGB(x, y);
								if (opaque && (rgb & 0xff000000) == 0) {
									rgb = backgroundColor;
								}
								windowImage.setRGB(x, y, rgb);
							}
						}
					}
				}

				if (w2 < wmax || h2 < hmax) {
					for (int x = 0; x < wmax; x++) {
						for (int y = 0; y < hmax; y++) {
							if ((x >= w2 || y >= h2) && (x < w1 && y < h1)) {
								int rgb = windowImage.getRGB(x, y);
								if (opaque && (rgb & 0xff000000) == 0) {
									rgb = backgroundColor;
								}
								bfileImage.setRGB(x, y, rgb);
							}
						}
					}
					fileImage = bfileImage;
				}
			}
			
			DiffImage diff = new DiffImage(fileImage, windowImage);
			diff.setImage1Label(file.getName());
			diff.setImage2Label("window");
			diff.setVisible(true);
		} catch (IORuntimeException ioe) {
			GuiUtils.errorDialog(this, "Unable to compare images", ioe);
		}
	}

	/**
	 * Compares the pixels of this canvas to the image stored in the given file.
	 */
	public void diff(String file) {
		diff(new File(file));
	}

	/**
	 * Compares the pixels of this canvas to the image stored in the given file.
	 * If ignoreWindowSize is true, allows the two windows' sizes to differ as
	 * long as they don't have any different drawn shapes between them.
	 */
	public void diff(String file, boolean ignoreWindowSize) {
		diff(new File(file), ignoreWindowSize);
	}

	/**
	 * Dispatches this mouse event to the uppermost graphical object for which
	 * the active point is within the object bounds.
	 *
	 * @usage gc.dispatchMouseEvent(MouseEvent e);
	 * @param e The event that triggered this response
	 * @noshow
	 */
	protected void dispatchMouseEvent(MouseEvent e) {
		GObject gobj = contents.getElementAt(e.getX(), e.getY(), true);
		MouseEvent newEvent = null;
		if (gobj != lastObject) {
			if (lastObject != null) {
				newEvent = new GMouseEvent(lastObject, MouseEvent.MOUSE_EXITED, e);
				lastObject.fireMouseListeners(newEvent);
			}
			if (gobj != null) {
				newEvent = new GMouseEvent(gobj, MouseEvent.MOUSE_ENTERED, e);
				gobj.fireMouseListeners(newEvent);
			}
		}
		lastObject = gobj;
		if (dragObject != null) {
			gobj = dragObject;
		}
		if (gobj != null) {
			int id = e.getID();
			if (id != MouseEvent.MOUSE_EXITED && id != MouseEvent.MOUSE_ENTERED) {
				// if (id != MouseEvent.MOUSE_DRAGGED || dragObject != null) {
				if (id == MouseEvent.MOUSE_PRESSED) {
					dragObject = gobj;
				} else if (id == MouseEvent.MOUSE_RELEASED) {
					dragObject = null;
				}
				newEvent = new GMouseEvent(gobj, id, e);
				gobj.fireMouseListeners(newEvent);
				// }
			}
		}
		if (newEvent != null && newEvent.isConsumed()) {
			e.consume();
		}
	}

	/**
	 * Draws an outlined version of the given shape.
	 */
	public void draw(Shape paramShape) {
		getOSG().draw(paramShape);
		conditionalRepaint();
	}

	/**
	 * Draws an outlined arc with the given coordinates.
	 */
	public void drawArc(double paramDouble1, double paramDouble2,
			double paramDouble3, double paramDouble4, double paramDouble5,
			double paramDouble6) {
		Arc2D.Double localDouble = new Arc2D.Double(paramDouble1, paramDouble2,
				paramDouble3, paramDouble4, paramDouble5, paramDouble6, 0);

		getOSG().draw(localDouble);
		conditionalRepaint();
	}

	/**
	 * Draws a line with the given endpoint coordinates.
	 */
	public void drawLine(double paramDouble1, double paramDouble2,
			double paramDouble3, double paramDouble4) {
		Line2D.Double localDouble = new Line2D.Double(paramDouble1,
				paramDouble2, paramDouble3, paramDouble4);
		getOSG().draw(localDouble);
		conditionalRepaint();
	}

	/**
	 * Draws an outlined oval with the given coordinates.
	 */
	public void drawOval(double paramDouble1, double paramDouble2,
			double paramDouble3, double paramDouble4) {
		Ellipse2D.Double localDouble = new Ellipse2D.Double(paramDouble1,
				paramDouble2, paramDouble3, paramDouble4);
		getOSG().draw(localDouble);
		conditionalRepaint();
	}

	/**
	 * Draws a line with the given polar coordinates, returning its starting endpoint.
	 */
	public GPoint drawPolarLine(double x0, double y0, double r, double theta) {
		theta = Math.toRadians(theta);
		double x1 = x0 + r * Math.cos(theta);
		double y1 = y0 - r * Math.sin(theta);
		drawLine(x0, y0, x1, y1);
		conditionalRepaint();
		return new GPoint(x1, y1);
	}

	/**
	 * Draws a line with the given polar coordinates, returning its starting endpoint.
	 */
	public GPoint drawPolarLine(GPoint p0, double r, double theta) {
		return drawPolarLine(p0.getX(), p0.getY(), r, theta);
	}
	
	/**
	 * Draws an outlined rectangle with the given coordinates.
	 */
	public void drawRect(double paramDouble1, double paramDouble2,
			double paramDouble3, double paramDouble4) {
		Rectangle2D.Double localDouble = new Rectangle2D.Double(paramDouble1,
				paramDouble2, paramDouble3, paramDouble4);
		getOSG().draw(localDouble);
		conditionalRepaint();
	}

	/**
	 * Draws a filled version of the given shape.
	 */
	public void fill(Shape paramShape) {
		getOSG().fill(paramShape);
		conditionalRepaint();
	}

	/**
	 * Draws a filled arc with the given coordinates.
	 */
	public void fillArc(double paramDouble1, double paramDouble2,
			double paramDouble3, double paramDouble4, double paramDouble5,
			double paramDouble6) {
		Arc2D.Double localDouble = new Arc2D.Double(paramDouble1, paramDouble2,
				paramDouble3, paramDouble4, paramDouble5, paramDouble6, 2);

		getOSG().fill(localDouble);
		getOSG().draw(localDouble);
		conditionalRepaint();
	}

	/**
	 * Draws a filled oval with the given coordinates.
	 */
	public void fillOval(double paramDouble1, double paramDouble2,
			double paramDouble3, double paramDouble4) {
		Ellipse2D.Double localDouble = new Ellipse2D.Double(paramDouble1,
				paramDouble2, paramDouble3, paramDouble4);
		getOSG().fill(localDouble);
		getOSG().draw(localDouble);
		conditionalRepaint();
	}

	/**
	 * Draws a filled rectangle with the given coordinates.
	 */
	public void fillRect(double paramDouble1, double paramDouble2,
			double paramDouble3, double paramDouble4) {
		Rectangle2D.Double localDouble = new Rectangle2D.Double(paramDouble1,
				paramDouble2, paramDouble3, paramDouble4);
		getOSG().fill(localDouble);
		getOSG().draw(localDouble);
		conditionalRepaint();
	}

	/**
	 * Returns the current setting of the auto-repaint flag as described in
	 * <a href="#setAutoRepaintFlag(boolean)"><code>setAutoRepaintFlag</code></a>.
	 *
	 * @usage if (gc.getAutoRepaintFlag()) . . .
	 * @return <code>true</code> if auto-repaint mode is enabled, and <code>false</code>
	 *          otherwise
	 */
	public boolean getAutoRepaintFlag() {
		return autoRepaint;
	}

	/**
	 * Returns the graphical object at the specified index, numbering from back
	 * to front in the the <i>z</i> dimension.
	 *
	 * @usage GObject gobj = gc.getElement(index);
	 * @param index The index of the component to return
	 * @return The graphical object at the specified index
	 */
	@SuppressWarnings("unchecked")
	public <T extends GObject> T getElement(int index) {
		return (T) contents.getElement(index);
	}

	/**
	 * Returns the topmost graphical object that contains the point
	 * (<code>x</code>, <code>y</code>), or <code>null</code> if no such
	 * object exists.
	 *
	 * @usage GObject gobj = gc.getElementAt(x, y);
	 * @param x The x-coordinate of the point being tested
	 * @param y The y-coordinate of the point being tested
	 * @return The graphical object at the specified location, or <code>null</code>
	 *         if no such object exists
	 */
	@SuppressWarnings("unchecked")
	public <T extends GObject> T getElementAt(double x, double y) {
		return (T) contents.getElementAt(x, y, false);
	}

	/**
	 * This version of getElementAt accepts a variable number of coordinate
	 * pairs (in x1, y1, x2, y2, x3, y3, ... order) and will return the
	 * topmost graphical objectfound at any of these pairs.
	 * The pairs are checked in the order they are passed.
	 * If no graphical object is found at any of these coordinate pairs,
	 * null is returned. 
	 */
	@SuppressWarnings("unchecked")
	public <T extends GObject> T getElementAt(double... coords) {
		if (coords.length == 0 || coords.length % 2 != 0) {
			throw new IllegalArgumentException(
					"number of coordinates passed must be even (must be a sequence of x/y pairs); you passed "
							+ Arrays.toString(coords));
		}
		for (int i = 0; i < coords.length; i += 2) {
			GObject obj = getElementAt(coords[i], coords[i + 1]);
			if (obj != null) {
				return (T) obj;
			}
		}
		return null;
	}

	/**
	 * Returns the topmost graphical object that contains the specified point,
	 * or <code>null</code> if no such object exists.
	 *
	 * @usage GObject gobj = gc.getElementAt(pt);
	 * @param pt The coordinates being tested
	 * @return The graphical object at the specified location, or <code>null</code>
	 *         if no such object exists
	 */
	@SuppressWarnings("unchecked")
	public final <T extends GObject> T getElementAt(GPoint pt) {
		return (T) getElementAt(pt.getX(), pt.getY());
	}

	/**
	 * Returns the number of graphical objects stored in this <code>GCanvas</code>.
	 *
	 * @usage int n = gc.getElementCount();
	 * @return The number of graphical objects in this <code>GCanvas</code>
	 */
	public int getElementCount() {
		return contents.getElementCount();
	}
	
//	@Override
//	public Graphics2D getGraphics() {
//		return (Graphics2D) bufferedImage.getGraphics();
//	}

	/**
	 * Returns the current setting of the native-arc flag as described in
	 * <a href="#setNativeArcFlag(boolean)"><code>setNativeArcFlag</code></a>.
	 *
	 * @usage if (gc.getNativeArcFlag()) . . .
	 * @return <code>true</code> if native arcs are enabled, and <code>false</code>
	 *          otherwise
	 */
	public boolean getNativeArcFlag() {
		return nativeArcFlag;
	}
	
	/**
	 * Returns the graphical pen for drawing on this canvas.
	 */
	public Graphics2D getOSG() {
		return osg;
	}
	
	/**
	 * Returns a Base64-encoded representation of the pixel data of this image.
	 */
	public String getPixelsAsString() {
		return GBufferedImage.toStringBase64(bufferedImage);
	}

	/**
	 * Returns the RGB color stored at the given (x, y) pixel, or 0 if that pixel falls outside the bounds of this canvas.
	 */
	public int getRGB(int x, int y) {
		int rgb = 0;
		if (inBounds(x, y)) {
			if (bufferedImage2 != null) {
				rgb = bufferedImage2.getRGB(x, y);
			} else if (bufferedImage != null) {
				rgb = bufferedImage.getRGB(x, y);
			}
			if (rgb == 0 && isOpaque()) {
				return getBackground().getRGB();
			}
		}
		return rgb;
	}
	
	/**
	 * Returns true if a graphical object exists that touches the given
	 * (x, y) pixel position, or false if no such object exists.
	 */
	public boolean hasElementAt(double x, double y) {
		return getElementAt(x, y) != null;
	}

	/**
	 * Returns true if a graphical object exists that touches any of the given
	 * (x, y) pixel positions, or false if no such object exists.
	 * This method accepts a variable number of coordinate
	 * pairs (in x1, y1, x2, y2, x3, y3, ... order).
	 * The pairs are checked in the order they are passed.
	 */
	public boolean hasElementAt(double... coords) {
		return getElementAt(coords) != null;
	}

	/**
	 * Whether the given (x, y) point falls within the bounds of this canvas, from (0, 0) .. (width-1, height-1) inclusive.
	 */
	public boolean inBounds(int x, int y) {
		return !(x < 0 || y < 0 || x >= bufferedImage.getWidth() || y >= bufferedImage.getHeight());
	}
	
	/**
	 * Sets whether this canvas uses anti-aliasing, which is smoothing and blending of neighboring pixels.
	 * Default true.
	 */
	public boolean isAntiAliasing() {
		return this.antialias;
	}
	
	/**
	 * Returns an <code>Iterator</code> that cycles through the elements within
	 * this container in the default direction, which is from back to front.
	 * You can also run the iterator in the opposite direction by using the
	 * <a href="#iterator(int)"><code>iterator</code></a><code>(</code><font
	 * size=-1><i>direction</i></font><code>)</code> form of this method.
	 *
	 * @usage Iterator<GObject> i = gc.iterator();
	 * @return An <code>Iterator</code> ranging over the elements of the
	 *         container from back to front
	 */
	public Iterator<GObject> iterator() {
		return new GIterator(this, GContainer.BACK_TO_FRONT);
	}

	/**
	 * Returns an <code>Iterator</code> that cycles through the elements
	 * within this container in the specified direction, which must be one
	 * of the constants <a href="GContainer.html#FRONT_TO_BACK"><code>GContainer.FRONT_TO_BACK</code></a>
	 * or <a href="GContainer.html#BACK_TO_FRONT"><code>GContainer.BACK_TO_FRONT</code></a>.<p>
	 *
	 * <p><pre><code>
	 * &nbsp;    for (Iterator&lt;GObject&gt; i = gc.iterator(direction); i.hasNext(); )
	 * </code></pre>
	 *
	 * @usage Iterator<GObject> i = gc.iterator(direction);
	 * @return An <code>Iterator</code> ranging over the elements of the
	 *         container in the specified direction
	 */
	public Iterator<GObject> iterator(int direction) {
		return new GIterator(this, direction);
	}

	/**
	 * Paints the canvas.  This method is not ordinarily called by clients.
	 *
	 * @usage gc.paintComponent(g);
	 * @param g The graphics context into which the canvas is painted
	 * @noshow
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (showPixelInfo && bufferedImage2 != null) {
			// special drawing if showPixelInfo is on (rare)
			// draw onto a special second buffered image so I can get the pixels
			clear(bufferedImage2);
			Graphics g2 = bufferedImage2.getGraphics();
			if (isOpaque()) {
				g2.setColor(getBackground());
				g2.fillRect(0, 0, getWidth(), getHeight());
			}
			g2.setColor(getForeground());
			if (bufferedImage != null) {
				g2.drawImage(bufferedImage, 0, 0, this);
			}
			contents.mapPaint(g2);
			g.drawImage(bufferedImage2, 0, 0, this);
			paintPixelInfo(g);
		} else {
			// standard drawing (much more common)
			if (bufferedImage != null) {
				if (isOpaque()) {
					g.setColor(getBackground());
					g.fillRect(0, 0, getWidth(), getHeight());
				}
				g.drawImage(bufferedImage, 0, 0, this);
			}
			g.setColor(getForeground());
			contents.mapPaint(g);
		}
		
		paintPixelGrid(g);
	}
	
	// draws a grid of horizontal and vertical lines to help debugging
	private void paintPixelGrid(Graphics g) {
		if (!showPixelGrid) {
			return;
		}
		
		int w = getWidth();
		int h = getHeight();
		
		if (g instanceof Graphics2D && isAntiAliasing()) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		g.setColor(PIXEL_GRID_COLOR);

		// horizontal lines
		for (int y = PIXEL_GRID_INCREMENT; y < h; y += PIXEL_GRID_INCREMENT) {
			g.drawLine(0, y, w, y);
		}
		
		// vertical lines
		for (int x = PIXEL_GRID_INCREMENT; x < w; x += PIXEL_GRID_INCREMENT) {
			g.drawLine(x, 0, x, h);
		}
	}
	
	// draws a label on the canvas showing the current mouse position's
	// (x, y) coordinate and current pixel color
	private void paintPixelInfo(Graphics g) {
		if (!showPixelInfo || lastMousePoint == null) {
			return;
		}
		int x = lastMousePoint.x;
		int y = lastMousePoint.y;
		int w = getWidth();
		int h = getHeight();
		
		// get info about the mouse pixel
		String pxInfo = "(x=" + x + ", y=" + y + ")";
		if (x >= 0 && x < w && y >= 0 && y < h) {
			int rgb = getRGB(x, y);
			pxInfo += ", color=" + GObject.colorNameFriendly(rgb);
		}
		
		// draw info on canvas in bottom-right (and println to text console)
		g.setColor(MOUSE_INFO_COLOR);
		g.setFont(MOUSE_INFO_FONT);
		FontMetrics met = g.getFontMetrics(MOUSE_INFO_FONT);
		int sw = met.stringWidth(pxInfo);
		// int sh = met.getHeight();
		// System.out.println(pxInfo);
		if (g instanceof Graphics2D && isAntiAliasing()) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		g.drawString(pxInfo, w - sw - 5, h - 2);
	}

	/**
	 * Removes the component from the canvas.
	 *
	 * @usage gc.remove(comp);
	 * @param comp The component to remove
	 */
	public void remove(Component comp) {
		super.remove(comp);
		conditionalRepaint();
	}

	/**
	 * Removes the top-most graphical object at the given (x, y) position from this container.
	 * If no graphical object is located at that position, does nothing.
	 * 
	 * @usage gc.remove(x, y);
	 */
	public void remove(double x, double y) {
		GObject gobj = getElementAt(x, y);
		if (gobj != null) {
			remove(gobj);
		}
	}

	/**
	 * Removes a graphical object from this <code>GCanvas</code>.
	 *
	 * @usage gc.remove(gobj);
	 * @param gobj The graphical object to remove
	 */
	public void remove(GObject gobj) {
		contents.remove(gobj);
		conditionalRepaint();
	}

	/**
	 * Removes the top-most graphical object at the given (x, y) position from this container.
	 * If no graphical object is located at that position, does nothing.
	 * 
	 * @usage gc.remove(pt);
	 */
	public void remove(GPoint pt) {
		GObject gobj = getElementAt(pt);
		if (gobj != null) {
			remove(gobj);
		}
	}

	/**
	 * Removes all graphical objects from this <code>GCanvas</code>.
	 *
	 * @usage gc.removeAll();
	 */
	public void removeAll() {
		contents.removeAll();
		super.removeAll();
		repaint();
	}
	
	/**
	 * Removes all graphical objects at the given (x, y) position from this container.
	 * If no graphical objects are located at that position, does nothing.
	 * 
	 * @usage gc.removeAll(x, y);
	 */
	public void removeAll(double x, double y) {
		GObject gobj = getElementAt(x, y);
		while (gobj != null) {
			remove(gobj);
			gobj = getElementAt(x, y);
		}
	}

	/**
	 * Removes all graphical objects at the given (x, y) positions from this container.
	 * If no graphical objects are located at that position, does nothing.
	 * 
	 * @usage gc.removeAll(x1, y1, x2, y2, ...);
	 */
	public void removeAll(double... coords) {
		if (coords.length == 0 || coords.length % 2 != 0) {
			throw new IllegalArgumentException(
					"number of coordinates passed must be even (must be a sequence of x/y pairs); you passed "
							+ Arrays.toString(coords));
		}
		for (int i = 0; i < coords.length; i += 2) {
			GObject gobj = getElementAt(coords[i], coords[i + 1]);
			while (gobj != null) {
				remove(gobj);
				gobj = getElementAt(coords[i], coords[i + 1]);
			}
		}
	}

	/**
	 * Removes all graphical objects at the given (x, y) position from this container.
	 * If no graphical objects are located at that position, does nothing.
	 * 
	 * @usage gc.removeAll(pt);
	 */
	public void removeAll(GPoint pt) {
		GObject gobj = getElementAt(pt);
		while (gobj != null) {
			remove(gobj);
			gobj = getElementAt(pt);
		}
	}

	/**
	 * Writes the contents of the canvas to the given file.
	 */
	public void save(File file) {
		BufferedImage img = toImage();
		MediaTools.saveImage(img, file);
	}
	
	/**
	 * Writes the contents of the canvas to the given file.
	 */
	public void save(String filename) {
		save(new File(filename));
	}
	
	/**
	 * Implements the <code>sendBackward</code> function from the <code>GContainer</code>
	 * interface.  Clients should not be calling this method, but the semantics of
	 * interfaces forces it to be exported.
	 * @noshow
	 */
	protected void sendBackward(GObject gobj) {
		contents.sendBackward(gobj);
		conditionalRepaint();
	}

	/**
	 * Implements the <code>sendForward</code> function from the <code>GContainer</code>
	 * interface.  Clients should not be calling this method, but the semantics of
	 * interfaces forces it to be exported.
	 * @noshow
	 */
	protected void sendForward(GObject gobj) {
		contents.sendForward(gobj);
		conditionalRepaint();
	}

	/**
	 * Implements the <code>sendToBack</code> function from the <code>GContainer</code>
	 * interface.  Clients should not be calling this method, but the semantics of
	 * interfaces forces it to be exported.
	 * @noshow
	 */
	protected void sendToBack(GObject gobj) {
		contents.sendToBack(gobj);
		conditionalRepaint();
	}

	/**
	 * Implements the <code>sendToFront</code> function from the <code>GContainer</code>
	 * interface.  Clients should not be calling this method, but the semantics of
	 * interfaces forces it to be exported.
	 * @noshow
	 */
	protected void sendToFront(GObject gobj) {
		contents.sendToFront(gobj);
		conditionalRepaint();
	}
	
	/**
	 * Sets whether this canvas uses anti-aliasing, which is smoothing and blending of neighboring pixels.
	 * Default true.
	 */
	public void setAntiAliasing(boolean antialias) {
		this.antialias = antialias;
		if (antialias) {
			osg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
			osg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		repaint();
	}

	/**
	 * Changes the setting of the auto-repaint flag.  By default, any change to a
	 * graphical object contained in this canvas automatically triggers a repaint
	 * of the canvas as a whole.  While this behavior makes it much easier to use
	 * the package, it has the disadvantage that repaint requests come much more
	 * frequently than necessary.  You can disable this feature by calling
	 * <code>setAutoRepaintFlag(false)</code>, but you must then make explicit
	 * calls to <code>repaint()</code> whenever you want to update the display.
	 * The advantage of this model is that you can then make many different changes
	 * and have them all appear at once with a single <code>repaint</code> call.
	 *
	 * @usage gc.setAutoRepaintFlag(state);
	 * @param state <code>true</code> to enable auto-repaint mode, and <code>false</code>
	 *              to disable it
	 */
	public void setAutoRepaintFlag(boolean state) {
		autoRepaint = state;
	}
	
	public void setBackground(Color color) {
		super.setBackground(color);
	}
	
	/**
	 * Sets this canvas to use a border of the given color, 1px thick.
	 */
	public void setBorder(Color color) {
		setBorder(BorderFactory.createLineBorder(color));
	}
	
	/**
	 * Sets this canvas to use a border of the given color and number of pixels thick.
	 */
	public void setBorder(Color color, int thickness) {
		setBorder(BorderFactory.createLineBorder(color, thickness));
	}

	public void setColor(Color color) {
		getOSG().setColor(color);
	}
	
	public void setColor(int paramInt) {
		setColor(new Color(paramInt));
	}
	
	@Override
	public void setForeground(Color color) {
		super.setForeground(color);
		getOSG().setColor(color);
	}
	
	public void setForeground(int paramInt) {
		setColor(new Color(paramInt));
	}
	
	/**
	 * Sets whether the redering code for <code>GArc</code> and <code>GOval</code> should use
	 * Java arcs.  By default, arcs and ovals are rendered using polygons and polylines
	 * to ensure that the same set of pixels is covered by the fill and frame.
	 * If this value is set to <code>true</code>, the renderers will use the native
	 * arc code, which is more efficient but less accurate.
	 *
	 * @usage gc.setNativeArcFlag(state);
	 * @param state <code>true</code> to enable native arcs, <code>false</code> to use polygons
	 */
	public void setNativeArcFlag(boolean state) {
		nativeArcFlag = state;
	}
	
	/**
	 * Sets a flag indicating whether this canvas is opaque, which means that it
	 * obscures anything behind it.  Setting this flag to <code>false</code> makes
	 * the <code>GCanvas</code> transparent, so that any other lightweight components
	 * behind it show through.
	 *
	 * @usage gc.setOpaque(flag);
	 * @param flag <code>true</code> to make this canvas opaque, and <code>false</code>
	 *             to make it transparent
	 * @noshow
	 */
	public void setOpaque(boolean flag) {
		super.setOpaque(flag);
		conditionalRepaint();
	}
	
	public void setPixelsFromString(String base64) {
		GBufferedImage.fromStringBase64(base64, bufferedImage);
		conditionalRepaint();
	}

	public void setRGB(int x, int y, int rgb) {
		setRGB(x, y, rgb, /* repaint */ false);
	}
	
	public void setRGB(int x, int y, int rgb, boolean repaint) {
		if (inBounds(x, y)) {
			bufferedImage.setRGB(x, y, rgb);
			if (repaint) {
				repaint();
			} else {
				conditionalRepaint();
			}
		}
	}
	
	/**
	 * Sets whether to display information on this canvas about the current pixel where
	 * the mouse pointer is resting.
	 * Displays the current pixel's (x, y) coordinate and color.
	 */
	public void setShowPixelGrid(boolean show) {
		this.showPixelGrid = show;
		repaint();
	}

	/**
	 * Sets whether to display information on this canvas about the current pixel where
	 * the mouse pointer is resting.
	 * Displays the current pixel's (x, y) coordinate and color.
	 */
	public void setShowPixelInfo(boolean show) {
		this.showPixelInfo = show;
		if (show) {
			int w = Math.max(1, getWidth());
			int h = Math.max(1, getHeight());
			bufferedImage2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			if (isAntiAliasing()) {
				Graphics2D g2 = (Graphics2D) bufferedImage2.getGraphics();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}
			bufferedImage2.getGraphics().setColor(getForeground());
		} else {
			bufferedImage2 = null;
			lastMousePoint = null;
		}
		repaint();
	}

	/**
	 * Pops up a dialog box to compare the contents of this canvas to an expected image file.
	 */
	public void showDiffDialog() {
		File dir = IOUtils.getExpectedOutputDirectory();
		showDiffDialog(dir);
	}
	
	/**
	 * Pops up a dialog box to compare the contents of this canvas to an expected image file.
	 * Starts the dialog box pointed at the given directory.
	 */
	public void showDiffDialog(File directory) {
		JFileChooser chooser = new JFileChooser(directory);
		chooser.setFileFilter(GuiUtils.getExtensionFileFilter("PNG images (*.png)", "png"));
		int result = chooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				diff(file);
			} catch (Exception ex) {
				GuiUtils.errorDialog(this, "Unable to save to " + file.getName(), ex);
			}
		}
	}
	
	/**
	 * Pops up a dialog box to compare the contents of this canvas to an expected image file.
	 * Starts the dialog box pointed at the given directory.
	 */
	public void showDiffDialog(String directory) {
		showDiffDialog(new File(directory));
	}
	
	/**
	 * Pops up a dialog box to save the contents of this canvas to a file.
	 * Returns the file saved to, or null if none.
	 */
	public File showSaveDialog() {
		return showSaveDialog(IOUtils.getCurrentDirectory());
	}
	
	/**
	 * Pops up a dialog box to save the contents of this canvas to a file.
	 * Starts the dialog box pointed at the given directory.
	 * Returns the file saved to, or null if none.
	 */
	public File showSaveDialog(File directory) {
		JFileChooser chooser = new JFileChooser(directory);
		chooser.setFileFilter(GuiUtils.getExtensionFileFilter("PNG images (*.png)", "png"));
		int result = chooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			
			// prompt to overwrite if file already exists
			if (file.exists()) {
				int confirm = JOptionPane.showConfirmDialog(this,
						"File already exists. Overwrite?", "Overwrite?", JOptionPane.YES_NO_OPTION);
				if (confirm != JOptionPane.YES_OPTION) {
					// abort without saving
					return null;
				}
			}
			
			try {
				save(file);
				return file;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Unable to save to " + file.getName()
						+ ": " + ex.getMessage(),
						"I/O Error", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Pops up a dialog box to save the contents of this canvas to a file.
	 * Starts the dialog box pointed at the given directory.
	 * Returns the file saved to, or null if none.
	 */
	public File showSaveDialog(String directory) {
		return showSaveDialog(new File(directory));
	}
	
	/**
	 * Returns the pixel contents of this canvas as a BufferedImage.
	 */
	public BufferedImage toImage() {
		return toImage(bufferedImage.getWidth(), bufferedImage.getHeight());
	}
	
	/**
	 * Returns the pixel contents of this canvas as a BufferedImage of the given size.
	 */
	public BufferedImage toImage(int width, int height) {
		// dump canvas into a BufferedImage
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, width, height);
		}
		paint(g);
		return img;
	}

	/**
	 * Reconstructs the enabledList list in the correct order.
	 *
	 * @usage gc.updateEnabledList();
	 */
	protected void updateEnabledList() {
		contents.updateEnabledList();
	}

	/**
	 * Creates a new <code>GMouseEvent</code> object with <code>gobj</code>
	 * effective source and <code>eventID</code>; all other fields are
	 * copied from the event <code>e</code>.  This method must be included
	 * in this class to avoid cross-file references to GMouseEvent from
	 * the GCompound class.
	 */
	static MouseEvent createMouseEvent(Object gobj, int eventID, MouseEvent e) {
		return new GMouseEvent(gobj, eventID, e);
	}

	/**
	 * This class fields mouse events that occur in the <code>GCanvas</code>.
	 */
	private class GCanvasListener implements MouseListener, MouseMotionListener, ComponentListener {
		/* Private instance variables */
		private GCanvas gCanvas;

		/**
		 * Creates a new listener object that watches for mouse events in the
		 * <code>GCanvas</code>.
		 *
		 * @usage GCanvasListener listener = new GCanvasListener(gc);
		 * @param gc The <code>GCanvas</code> object to which this listens
		 */
		public GCanvasListener(GCanvas gc) {
			gCanvas = gc;
		}

		/* ComponentListener interface */
		public void componentHidden(ComponentEvent e) {
			// empty
		}
		
		public void componentMoved(ComponentEvent e) {
			// empty
		}

		public void componentResized(ComponentEvent e) {
			BufferedImage oldImage = bufferedImage;
			int w = Math.max(1, getWidth());
			int h = Math.max(1, getHeight());
			if (w == oldImage.getWidth() && h == oldImage.getHeight()) {
				return;
			}
			
			bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			if (showPixelInfo) {
				bufferedImage2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			}
			osg = (Graphics2D) bufferedImage.getGraphics();
			if (isAntiAliasing()) {
				osg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}

			// draw background
//			if (isOpaque()) {
//				osg.setColor(getBackground());
//				osg.fillRect(0, 0, w, h);
//			}
//			osg.setColor(Color.BLACK);
			
			// copy over
			if (oldImage != null) {
				osg.drawImage(oldImage, 0, 0, gCanvas);
			}
			
			if (gCanvas.isShowing()) {
				// gCanvas.repaint();
			}
		}

		public void componentShown(ComponentEvent e) {
			// empty
		}
		
		/* MouseListener/MouseMotionListener interfaces */
		public void mouseClicked(MouseEvent e) {
			gCanvas.dispatchMouseEvent(e);
		}

		public void mouseDragged(MouseEvent e) {
			gCanvas.dispatchMouseEvent(e);
			if (showPixelInfo) {
				lastMousePoint = e.getPoint();
				repaint();
			}
		}

		public void mouseEntered(MouseEvent e) {
			gCanvas.dispatchMouseEvent(e);
		}

		public void mouseExited(MouseEvent e) {
			gCanvas.dispatchMouseEvent(e);
		}

		public void mouseMoved(MouseEvent e) {
			gCanvas.dispatchMouseEvent(e);
			if (showPixelInfo) {
				lastMousePoint = e.getPoint();
				repaint();
			}
		}

		public void mousePressed(MouseEvent e) {
			gCanvas.requestFocus();
			gCanvas.dispatchMouseEvent(e);
		}
		
		public void mouseReleased(MouseEvent e) {
			gCanvas.dispatchMouseEvent(e);
		}
	}

	private static class GMouseEvent extends MouseEvent {
		/* Private instance variables */
		private Object effectiveSource;

		/**
		 * Creates a new <code>GMouseEvent</code> object with <code>gobj</code>
		 * effective source and <code>eventID</code>; all other fields are
		 * copied from the event <code>e</code>.
		 */
		public GMouseEvent(Object gobj, int eventID, MouseEvent e) {
			super(e.getComponent(), eventID, e.getWhen(), e.getModifiers(),
					e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger());
			effectiveSource = gobj;
		}

		/**
		 * Overrides <code>getSource</code> to return the effective source of this event,
		 * which will typically be a <code>GObject</code> rather than the <code>GCanvas</code>
		 * that triggered the event.
		 */
		public Object getSource() {
			return effectiveSource;
		}

		/**
		 * Overrides <code>getComponent</code> to return the <code>GCanvas</code>
		 * that triggered the event.
		 */
		public Component getComponent() {
			return (Component) super.getSource();
		}

		/**
		 * Overrides <code>toString</code> to display the correct source for this event.
		 */
		public String toString() {
			return getClass().getName() + "[" + paramString() + "] on " + getSource();
		}
	}
}
