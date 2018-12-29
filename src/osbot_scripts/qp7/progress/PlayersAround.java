package osbot_scripts.qp7.progress;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.database.DatabaseUtilities;

public class PlayersAround extends MethodProvider implements Runnable {

	public PlayersAround(QuestStep quest) {
		this.quest = quest;
	}

	private boolean run = true;

	public int aroundMine = -1;

	private QuestStep quest;

	@Override
	public void run() {
		while (run) {
			try {
				log("Trying to calculate players around me that are mine... ");

				aroundMine = getAccountsThatsAreMy();

				Thread.sleep(35_000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		run = false;
	}

	int getPlayersAroundExceptMe() {
		return getPlayers().getAll().stream().filter(Objects::nonNull)
				.filter(me -> getQuest().getEvent() != null
						&& !me.getName().equalsIgnoreCase(getQuest().getEvent().getActualUsername())
						&& me.getX() == myPlayer().getX() && me.getY() == myPlayer().getY())
				.collect(Collectors.toList()).size();
	}

	List<Player> getPlayersAroundExceptMeList() {
		return getPlayers().getAll().stream().filter(Objects::nonNull)
				.filter(me -> getQuest().getEvent() != null
						&& !me.getName().equalsIgnoreCase(getQuest().getEvent().getActualUsername())
						&& me.getX() == myPlayer().getX() && me.getY() == myPlayer().getY())
				.collect(Collectors.toList());
	}

	private int getAccountsThatsAreMy() {
		int mine = 0;
		for (Player player : getPlayersAroundExceptMeList()) {
			if (getClient().isLoggedIn() && player != null && player.getName() != null) {
				boolean isMine = DatabaseUtilities.accountContainsInDatabase(this, player.getName(),
						getQuest().getEvent());
				log("Found account: " + player + " mine: " + isMine);
				if (isMine) {
					mine++;
				}
			}
		}
		return mine;
	}

	/**
	 * @return the quest
	 */
	public QuestStep getQuest() {
		return quest;
	}

	/**
	 * @param quest
	 *            the quest to set
	 */
	public void setQuest(QuestStep quest) {
		this.quest = quest;
	}
}