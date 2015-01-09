package org.shininet.bukkit.expmoney;

public class PlayerNBTException extends Exception {
	private static final long serialVersionUID = 4564419109098084472L;
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