package bno.swing2.awt;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import javax.swing.JLabel;

/**
 * This class represents a hyperlink similar to those known from the www.
 * 
 * @author Marvin Bruns
 *
 */
public class BLink extends JLabel {

	private static final long serialVersionUID = 1L;

	private Color normalColor = new Color(6, 69, 173);
	private Color overColor = new Color(6, 0, 173);
	private Color visitedColor = new Color(11, 0, 128);
	private Color beforeOverColor = null;

	private URI link = null;

	private boolean visited = false;

	private int clickCount = 1;

	/**
	 * Create an empty link. No visible text and no link to open.
	 */
	public BLink() {

		super();

		create();
	}

	/**
	 * Create an Link with specific URI to open after a click. The visible text
	 * will be the textual representation of this URI.
	 * 
	 * @param link
	 *            Link to open after clicking this component.
	 */
	public BLink(URI link) {

		this(link.toString(), link);
	}

	/**
	 * Create an Link with specific URI to open after a click and the visible
	 * text. The visible text is unbound to the URI.
	 * 
	 * @param text
	 *            The visible text.
	 * @param link
	 *            Link to open after clicking this component.
	 */
	public BLink(String text, URI link) {

		super(text);

		this.setLink(link);
		create();
	}

	private void create() {

		setVisited(false);
		setClickCount(1);

		setForeground(getNormalColor());
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {

				underline(true);
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				setForegroundOverColor(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {

				underline(false);
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				setForegroundOverColor(false);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == getClickCount()) {

					visit();
				}
			}
		});
	}

	private void setForegroundOverColor(boolean mouseOver) {

		// TODO

		if (mouseOver) {

			setForeground(getOverColor());
		} else {

			setForeground(beforeOverColor);
		}
	}

	private void updateForegroundColor() {
		// TODO
	}

	private void openUri() throws IOException {

		URI link = getLink();

		if (link == null) {
			return;
		}

		if (Desktop.isDesktopSupported()) {

			Desktop desktop = Desktop.getDesktop();

			if (desktop.isSupported(Desktop.Action.BROWSE)) {

				desktop.browse(link);
			}

		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void underline(boolean underline) {

		int value = (underline) ? TextAttribute.UNDERLINE_ON : -1;

		Font font = getFont();

		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, value);

		font = font.deriveFont(attributes);
		setFont(font);
	}

	/**
	 * Does the same as if you click on this link. It will be activated and
	 * visited if possible. {@code Desktop.isSupported()} must be true in order
	 * to visit the known URI.
	 */
	public void visit() {
	
		setVisited(true);
		setForeground(getVisitedColor());
	
		try {
			openUri();
		} catch (IOException e) {
	
			throw new RuntimeException(e);
		}
	}

	public Color getNormalColor() {
		return normalColor;
	}

	/**
	 * Change the Color of the Link in his normal state.
	 * 
	 * @param normalColor
	 *            Color of the link in normal state.
	 */
	public void setNormalColor(Color normalColor) {
		this.normalColor = normalColor;
		updateForegroundColor();
	}

	public Color getVisitedColor() {
		return visitedColor;
	}

	/**
	 * Change the Color of the Link in his visited state.
	 * 
	 * @param normalColor
	 *            Color of the link in visited state.
	 */
	public void setVisitedColor(Color visitedColor) {
		this.visitedColor = visitedColor;
		updateForegroundColor();
	}

	public Color getOverColor() {
		return overColor;
	}

	/**
	 * Change the Color of the Link in his over state.
	 * 
	 * @param normalColor
	 *            Color of the link in over state.
	 */
	public void setOverColor(Color overColor) {
		this.overColor = overColor;
		updateForegroundColor();
	}

	public boolean isVisited() {
		return visited;
	}

	protected void setVisited(boolean visited) {
		this.visited = visited;
	}

	public URI getLink() {
		return link;
	}

	public void setLink(URI link) {
		this.link = link;
	}

	public int getClickCount() {
		return clickCount;
	}

	/**
	 * Change the amount of clicks which are necessary to activate this link.
	 * 
	 * @param clickCount
	 *            Amount of clicks
	 */
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

}
