package graphics2D;

import gameCore.Direction;


public interface PlayerListener {
	public void itemAdded(PlayerEvent e);
	public void itemDropped(PlayerEvent e);
	public void statsChanged(PlayerEvent e);
	public void positionChanged(PlayerEvent e, Direction backDir);
	public void playerRestored(PlayerEvent e);
}
