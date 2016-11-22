package game;

public interface AI {
	public abstract void ai(Game game,Desk.Player me);
	public abstract boolean getWuxie(Game game,Desk.Player me,Desk.Player p);
}
