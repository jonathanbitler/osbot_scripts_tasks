package osbot_scripts.qp7.progress;

import org.osbot.rs07.script.MethodProvider;

public class TradingBody extends MethodProvider {

	private QuestStep quest;
	
	public TradingBody(QuestStep quest) {
		this.setQuest(quest);
	}
	
	public void handleRetrades() {
		
	}

	/**
	 * @return the quest
	 */
	public QuestStep getQuest() {
		return quest;
	}

	/**
	 * @param quest the quest to set
	 */
	public void setQuest(QuestStep quest) {
		this.quest = quest;
	}
	
}
