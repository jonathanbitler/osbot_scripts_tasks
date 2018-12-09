package osbot_scripts;

import java.awt.Graphics2D;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import osbot_scripts.bot.utils.BotCommands;
import osbot_scripts.config.Config;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.events.EnableFixedModeEvent;
import osbot_scripts.events.LoginEvent;
import osbot_scripts.events.MandatoryEventsExecution;
import osbot_scripts.framework.AccountStage;
import osbot_scripts.login.LoginHandler;
import osbot_scripts.qp7.progress.ThreadDemo;
import osbot_scripts.sections.BankGuideSection;
import osbot_scripts.sections.CharacterCreationSection;
import osbot_scripts.sections.ChurchGuideSection;
import osbot_scripts.sections.CombatGuideSection;
import osbot_scripts.sections.CookingGuideSection;
import osbot_scripts.sections.GuilinorGuideSection;
import osbot_scripts.sections.MiningGuideSection;
import osbot_scripts.sections.QuestGuideSection;
import osbot_scripts.sections.SurvivalExpertSection;
import osbot_scripts.sections.TutorialSection;
import osbot_scripts.sections.WizardGuideSection;
import osbot_scripts.sections.decide.CheckInWhatArea;
import osbot_scripts.sections.total.progress.MainState;

@ScriptManifest(author = "pim97@github & dormic@osbot", info = "completes the tutorial island for you", logo = "", name = "TUT_ISLAND", version = 0)
public class TutorialScript extends Script {

	/**
	 * Auto disabled or not
	 */
	private boolean isAudioDisabled;

	/**
	 * The current mainstate
	 */
	public static MainState mainState = null;

	/**
	 * Creating a character with random outfit
	 */
	private final TutorialSection characterCreationSection = new CharacterCreationSection();

	/**
	 * Talking to the guilinor guide
	 */
	private final TutorialSection guilinorGuideSection = new GuilinorGuideSection();

	/**
	 * Survival Expert section
	 */
	private final TutorialSection survivalExpertSection = new SurvivalExpertSection();

	/**
	 * 
	 */
	private final TutorialSection cookingGuideSection = new CookingGuideSection();

	/**
	 * 
	 */
	private final TutorialSection questGuideSection = new QuestGuideSection();

	/**
	 * 
	 */
	private final TutorialSection miningGuideSection = new MiningGuideSection();

	/**
	 * 
	 */
	private final TutorialSection combatGuideSection = new CombatGuideSection();

	/**
	 * 
	 */
	private final TutorialSection bankingAreaSection = new BankGuideSection();

	/**
	 * 
	 */
	private final TutorialSection churchGuideSection = new ChurchGuideSection();

	/**
	 * 
	 */
	private final TutorialSection wizardGuideSection = new WizardGuideSection();

	/**
	 * 
	 */
	private static final Area TUT_ISLAND_AREA = new Area(
			new int[][] { { 3049, 3128 }, { 3032, 3090 }, { 3054, 3037 }, { 3146, 3048 }, { 3181, 3101 },
					{ 3163, 3129 }, { 3137, 3137 }, { 3131, 3145 }, { 3103, 3140 }, { 3087, 3156 }, { 3054, 3138 } });

	private static final Area TUT_ISLAND_AREA_CAVE = new Area(new int[][] { { 3067, 9521 }, { 3096, 9540 },
			{ 3128, 9540 }, { 3125, 9498 }, { 3085, 9482 }, { 3062, 9500 } });

	private MandatoryEventsExecution events = new MandatoryEventsExecution(this, null);

	private LoginEvent login;

	/**
	 * Loops
	 */
	@Override
	public int onLoop() throws InterruptedException {

		if (!getClient().isLoggedIn()) {
			return 1000;
		}

		if (mainState == null) {
			mainState = CheckInWhatArea.getState(this);
		}

		// If the person is not logged in anymore, but the client is still open, then
		// exit the client
		if ((!getClient().isLoggedIn()) && (System.currentTimeMillis() - getLogin().getStartTime() > 200_000)) {
			log("Person wasn't logged in anymore, logging out!");
			Thread.sleep(5000);
			BotCommands.waitBeforeKill((MethodProvider) this, "WASN'T LOGGED IN ANYMORE, LOGGING OUT TUTORIAL ISLAND");
		}

		if (getClient().isLoggedIn() && getConfigs().get(281) >= 3 && getConfigs().get(281) < 650) {
			events.fixedMode();
			events.fixedMode2();
			events.executeAllEvents();
			if (!EnableFixedModeEvent.isFixedModeEnabled(this)) {
				if (execute(new EnableFixedModeEvent()).hasFinished()) {
					System.out.println("Set client to fixed mode, finished");
					BotCommands.waitBeforeKill((MethodProvider) this, "SET CLIENT TO FIXED MODE TUTORIAL ISLAND");
				}
			}
		}

		if (!getClient().isLoggedIn() && getConfigs().get(281) > 0) {
			BotCommands.killProcess((MethodProvider) this, (Script) this, "");
		}

		if ((!TUT_ISLAND_AREA.contains(myPlayer()) && !TUT_ISLAND_AREA_CAVE.contains(myPlayer()))
				|| (new Area(new int[][] { { 3221, 3228 }, { 3221, 3209 }, { 3246, 3210 }, { 3245, 3229 } })
						.contains(myPlayer()))) {
			log("Succesfully completed!");
			DatabaseUtilities.updateStageProgress(this, AccountStage.QUEST_COOK_ASSISTANT.name(), 0,
					getLogin().getUsername());
			BotCommands.killProcess((MethodProvider) this, (Script) this,
					"BECAUSE PLAYER IS NOT ON TUTORIAL ISLAND, SETTING TO COOKS ASSISTANT");
		}

		log(mainState);

		if (mainState == MainState.CREATE_CHARACTER_DESIGN) {
			characterCreationSection.onLoop();
		} else if (mainState == MainState.TALK_TO_GIELINOR_GUIDE_ONE) {
			guilinorGuideSection.onLoop();
		} else if (mainState == MainState.SURVIVAL_EXPERT) {
			survivalExpertSection.onLoop();
		} else if (mainState == MainState.COOKING_GUIDE_SECTION) {
			cookingGuideSection.onLoop();
		} else if (mainState == MainState.QUEST_SECTION) {
			questGuideSection.onLoop();
		} else if (mainState == MainState.MINING_SECTION) {
			miningGuideSection.onLoop();
		} else if (mainState == MainState.COMBAT_SECTION) {
			combatGuideSection.onLoop();
		} else if (mainState == MainState.BANKING_AREA_SECTION) {
			bankingAreaSection.onLoop();
		} else if (mainState == MainState.CHURCH_GUIDE_SECTION) {
			churchGuideSection.onLoop();
		} else if (mainState == MainState.WIZARD_GUIDE_SECTION) {
			wizardGuideSection.onLoop();
		} else if (mainState == MainState.IN_LUMBRIDGE) {
			// while (getClient().isLoggedIn()) {
			// getLogoutTab().logOut();
			// stop();
			// Thread.sleep(5000);
			log("Trying to logout...");
			// }
		}

		return random(400, 800);
	}

	private ThreadDemo demo;

	@Override
	public void onStart() throws InterruptedException {
		if (!Config.NO_LOGIN) {
			login = LoginHandler.login(this, getParameters());
			login.setScript("TUT_ISLAND");
		}
		if (events.getLogin() == null) {
			events.setLogin(login);
		}
		getCharacterCreationSection().login = login;

		// demo = new ThreadDemo();
		// demo.exchangeContext(this.getBot());
		// demo.setLoginEvent(getLogin());
		// new Thread(demo).start();

		getCharacterCreationSection().exchangeContext(getBot());
		getGuilinorGuideSection().exchangeContext(getBot());
		getSurvivalExpertSection().exchangeContext(getBot());
		getCookingGuideSection().exchangeContext(getBot());
		getQuestGuideSection().exchangeContext(getBot());
		getMiningGuideSection().exchangeContext(getBot());
		getCombatGuideSection().exchangeContext(getBot());
		getBankingAreaSection().exchangeContext(getBot());
		getChurchGuideSection().exchangeContext(getBot());
		getWizardGuideSection().exchangeContext(getBot());

		// prevents script from skipping character customization
		sleep(4000);
	}

	@Override
	public void onExit() throws InterruptedException {

	}

	@Override
	public void onPaint(Graphics2D g) {
		getMouse().setDefaultPaintEnabled(true);
	}

	/**
	 * @return the guilinorGuideSection
	 */
	public TutorialSection getGuilinorGuideSection() {
		return guilinorGuideSection;
	}

	/**
	 * 
	 * @return
	 */
	public TutorialSection getCharacterCreationSection() {
		return characterCreationSection;
	}

	public TutorialSection getSurvivalExpertSection() {
		return survivalExpertSection;
	}

	/**
	 * @return the cookingGuideSection
	 */
	public TutorialSection getCookingGuideSection() {
		return cookingGuideSection;
	}

	/**
	 * @return the questGuideSection
	 */
	public TutorialSection getQuestGuideSection() {
		return questGuideSection;
	}

	/**
	 * @return the miningGuideSection
	 */
	public TutorialSection getMiningGuideSection() {
		return miningGuideSection;
	}

	/**
	 * @return the combatGuideSection
	 */
	public TutorialSection getCombatGuideSection() {
		return combatGuideSection;
	}

	/**
	 * @return the bankingAreaSection
	 */
	public TutorialSection getBankingAreaSection() {
		return bankingAreaSection;
	}

	/**
	 * @return the churchGuideSection
	 */
	public TutorialSection getChurchGuideSection() {
		return churchGuideSection;
	}

	public TutorialSection getWizardGuideSection() {
		return wizardGuideSection;
	}

	/**
	 * @return the login
	 */
	public LoginEvent getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(LoginEvent login) {
		this.login = login;
	}

}
