package osbot_scripts.testscripts;

import org.osbot.rs07.script.Script;

import osbot_scripts.events.LoginEvent;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.framework.GrandExchangeTask;
import osbot_scripts.framework.parts.BankItem;
import osbot_scripts.qp7.progress.Ge2;
import osbot_scripts.qp7.progress.QuestStep;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.taskhandling.TaskHandler;

public class Test extends QuestStep {

	public Test(int questStartNpc, int configQuestId, LoginEvent event, Script script) {
		super(questStartNpc, configQuestId, AccountStage.QUEST_COOK_ASSISTANT, event, script, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onStart() {

		getTaskHandler().getTasks().put(getTaskHandler().getTasks().size(), new GrandExchangeTask(this, new BankItem[] {

//				new BankItem("Copper ore", 436, 4, 20000, true), new BankItem("Iron ore", 440, 2, 250, true),
				new BankItem("Clay", 434, 6, 160, true),

		}, new BankItem[] {

				new BankItem("Clay", 434, 20, 200, true),
//				new BankItem("Uncut diamond", 1617, 1000, 1, true),
//				new BankItem("Uncut emerald", 1621, 1000, 1, true), new BankItem("Uncut ruby", 1619, 1000, 1, true),
//				new BankItem("Uncut sapphire", 1623, 1000, 1, true),

		}, getEvent(), getScript(), this));

	}
	
	@Override
	public void timeOutHandling(TaskHandler tasks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoop() throws InterruptedException {

	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MainState getNextMainState() {
		// TODO Auto-generated method stub
		return null;
	}

}
