package levelEditor;

public interface TileInfoListener {
	/**
	 * Fired when data is added to the source model.
	 */
	public void dataAdded(TileInfoEvent e);
	/**
	 * Fired when data is removed from the source model.
	 */
	public void dataRemoved(TileInfoEvent e);
	/**
	 * Fired when data is cleared (en-masse without single remove events) from the source model.
	 */
	public void dataCleared(TileInfoEvent e);
	/**
	 * Fired when data is synched between the source TileInfoModel and the Tile it represents.
	 */
	public void dataSynched(TileInfoEvent e);
}
