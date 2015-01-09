package org.shininet.bukkit.expcurrency;

public class PlayerNBTException extends Exception {
	private static final long serialVersionUID = 3690248731098763971L;
	public PlayerNBTException() {
        super();
    }
    public PlayerNBTException(String message) {
        super(message);
    }
    public PlayerNBTException(String message, Throwable cause) {
        super(message, cause);
    }
    public PlayerNBTException(Throwable cause) {
        super(cause);
    }
}