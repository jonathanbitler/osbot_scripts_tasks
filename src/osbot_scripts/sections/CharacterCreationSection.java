package osbot_scripts.sections;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.TutorialScript;
import osbot_scripts.events.CachedWidget;
import osbot_scripts.sections.progress.CharacterCreationSectionProgress;
import osbot_scripts.sections.total.progress.MainState;
import osbot_scripts.util.Sleep;

public class CharacterCreationSection extends TutorialSection {

    private final CachedWidget creationScreenWidget = new CachedWidget("Head");
	/**
	 * Current progress
	 */
	public CharacterCreationSectionProgress progress = CharacterCreationSectionProgress.CREATING_RANDOM_CHARACTER;

	/**
	 * Parent ID for the inferface when creating a character
	 */
	public static final int DESIGN_CHARACTER_PARENT_ID = 269;

	/**
	 * Time between selecting a specific part of a character
	 */
	public static final int TIME_BETWEEN_DESIGN_CHARACTER = 2000;

	/**
	 * Constructor
	 * 
	 * @param mainState
	 */
	public CharacterCreationSection() {
		super(null);
	}

	/**
	 * Loop
	 */
	@Override
	public final void onLoop() throws InterruptedException {
		if (!isCompleted() && getProgress() <= 1) {
			createCharacterDesign();
			log("Not yet completed");
		} else {
			TutorialScript.mainState = getNextMainState();
			log("Section completed!");
		}
	}

	public void typeCharacterName() throws InterruptedException {
		RS2Widget widget = getWidgets().get(558, 3, 0);
		if (widget != null && widget.isVisible()) {
			log("1");
			RS2Widget inputText = getWidgets().get(558, 7);

			Sleep.sleepUntil(() -> inputText != null && inputText.isVisible(), 2000);
			if (inputText != null && inputText.isVisible()) {
				log("2");
				inputText.interact();

				RS2Widget typeBar = getWidgets().get(162, 40);
				Sleep.sleepUntil(() -> typeBar != null && typeBar.isVisible(), 10000);

				if (typeBar != null && typeBar.isVisible()) {
					log("3");
					getKeyboard().typeString(login.getActualUsername(), true);

					RS2Widget setName = getWidgets().get(558, 18);

					Sleep.sleepUntil(() -> setName != null && setName.isVisible()
							&& getColorPicker().isColorAt(346, 217, new Color(0, 255, 0)), 10000);

					if (setName != null && setName.isVisible()) {
						int a = 0;
						while (a < 3) {
							a++;

							sleep(3000);
							log("loop: " + a);
							setName.interact();
						}
					}
					log("4");

					Sleep.sleepUntil(() -> widget != null && !widget.isVisible(), 10000);

					log("5");
					if (widget != null && widget.isVisible()) {
						RS2Widget suggestionName = getWidgets().get(558, 14);
						if (suggestionName != null && suggestionName.isVisible()) {
							suggestionName.interact("Set name");

							Sleep.sleepUntil(() -> setName != null && setName.isVisible(), 10000);
							if (setName != null && setName.isVisible()) {
								int a = 0;
								while (a < 3) {
									a++;

									sleep(3000);
									log("loop: " + a);
									setName.interact();
								}
							}
						}

					}
				}
			}
		}
	}

	/**
	 * Returns the next state
	 */
	@Override
	public MainState getNextMainState() {
		// TODO Auto-generated method stub
		return MainState.TALK_TO_GIELINOR_GUIDE_ONE;
	}

	/**
	 * Is the section completed?
	 */
	@Override
	public boolean isCompleted() {
		return getProgress() == 0 && getWidgets().get(269, 96) == null ? true : false;
	}
	
	private void createRandomCharacter() throws InterruptedException {
        // letting all the widgets show up
        sleep(2000);

        if (new Random().nextInt(2) == 1) {
            getWidgets().getWidgetContainingText("Female").interact();
        }

        final RS2Widget[] childWidgets = getWidgets().getWidgets(creationScreenWidget.get(getWidgets()).get().getRootId());
        Collections.shuffle(Arrays.asList(childWidgets));

        for (final RS2Widget childWidget : childWidgets) {
            if (childWidget.getToolTip() == null) {
                continue;
            }
            if (childWidget.getToolTip().contains("Change") || childWidget.getToolTip().contains("Recolour")) {
                clickRandomTimes(childWidget);
            }
        }

        if (getWidgets().getWidgetContainingText("Accept").interact()) {
            Sleep.sleepUntil(() -> !isCreationScreenVisible(), 3000, 600);
        }
    }
	
	private boolean isCreationScreenVisible() {
        return creationScreenWidget.get(getWidgets()).filter(RS2Widget::isVisible).isPresent();
    }

    private void clickRandomTimes(final RS2Widget widget) throws InterruptedException {
        int clickCount = new Random().nextInt(8);

        for (int i = 0; i < clickCount; i++) {
            if (widget.interact()) {
                MethodProvider.sleep(150);
            }
        }
    }

	/**
	 * Creates an unique character design
	 * 
	 * @throws InterruptedException
	 */
	private void createCharacterDesign() throws InterruptedException {
		typeCharacterName();
		
		createRandomCharacter();

//		if (progress == CharacterCreationSectionProgress.CREATING_RANDOM_CHARACTER) {
//			progress = CharacterCreationSectionProgress.CHOOSING_HEAD;
//		} else if (progress == CharacterCreationSectionProgress.CHOOSING_HEAD) {
//			RS2Widget designCharacterWidgetHead = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 113);
//			if (designCharacterWidgetHead != null && designCharacterWidgetHead.isVisible()) {
//				Random rand = new Random();
//				int random = rand.nextInt(3) + 1;
//				for (int i = 0; i < random; i++) {
//					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
//					designCharacterWidgetHead.interact("Change head");
//
//					RS2Widget recolor = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 121);
//					if (recolor != null) {
//						recolor.interact("Recolour hair");
//					}
//				}
//			}
//			progress = CharacterCreationSectionProgress.CHOOSING_JAW;
//		} else if (progress == CharacterCreationSectionProgress.CHOOSING_JAW) {
//			RS2Widget designCharacterWidgetJaw = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 114);
//			if (designCharacterWidgetJaw != null && designCharacterWidgetJaw.isVisible()) {
//				Random rand = new Random();
//				int random = rand.nextInt(3) + 1;
//				for (int i = 0; i < random; i++) {
//					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
//					designCharacterWidgetJaw.interact("Change jaw");
//				}
//			}
//			progress = CharacterCreationSectionProgress.CHOOSING_TORSO;
//		} else if (progress == CharacterCreationSectionProgress.CHOOSING_TORSO) {
//			RS2Widget designCharacterWidgetTorso = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 115);
//			if (designCharacterWidgetTorso != null && designCharacterWidgetTorso.isVisible()) {
//				Random rand = new Random();
//				int random = rand.nextInt(3) + 1;
//				for (int i = 0; i < random; i++) {
//					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
//					designCharacterWidgetTorso.interact("Change torso");
//
//					RS2Widget recolor = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 127);
//					if (recolor != null) {
//						recolor.interact("Recolour torso");
//					}
//				}
//			}
//			progress = CharacterCreationSectionProgress.CHOOSING_ARMS;
//		} else if (progress == CharacterCreationSectionProgress.CHOOSING_ARMS) {
//			RS2Widget designCharacterWidgetArms = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 116);
//			if (designCharacterWidgetArms != null && designCharacterWidgetArms.isVisible()) {
//				Random rand = new Random();
//				int random = rand.nextInt(3) + 1;
//				for (int i = 0; i < random; i++) {
//					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
//					designCharacterWidgetArms.interact("Change arms");
//				}
//			}
//			progress = CharacterCreationSectionProgress.CHOOSING_HANDS;
//		} else if (progress == CharacterCreationSectionProgress.CHOOSING_HANDS) {
//			RS2Widget designCharacterWidgetHands = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 117);
//			if (designCharacterWidgetHands != null && designCharacterWidgetHands.isVisible()) {
//				Random rand = new Random();
//				int random = rand.nextInt(3) + 1;
//				for (int i = 0; i < random; i++) {
//					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
//					designCharacterWidgetHands.interact("Change hands");
//				}
//			}
//			progress = CharacterCreationSectionProgress.CHOOSING_LEGS;
//		} else if (progress == CharacterCreationSectionProgress.CHOOSING_LEGS) {
//			RS2Widget designCharacterWidgetLegs = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 118);
//			if (designCharacterWidgetLegs != null && designCharacterWidgetLegs.isVisible()) {
//				Random rand = new Random();
//				int random = rand.nextInt(3) + 1;
//				for (int i = 0; i < random; i++) {
//					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
//					designCharacterWidgetLegs.interact("Change legs");
//
//					RS2Widget recolor = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 129);
//					if (recolor != null) {
//						recolor.interact("Recolour legs");
//					}
//				}
//			}
//			progress = CharacterCreationSectionProgress.CHOOSING_FEET;
//		} else if (progress == CharacterCreationSectionProgress.CHOOSING_FEET) {
//			RS2Widget designCharacterWidgetFeet = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 119);
//			if (designCharacterWidgetFeet != null && designCharacterWidgetFeet.isVisible()) {
//				Random rand = new Random();
//				int random = rand.nextInt(3) + 1;
//				for (int i = 0; i < random; i++) {
//					sleep(rand.nextInt(TIME_BETWEEN_DESIGN_CHARACTER));
//					designCharacterWidgetFeet.interact("Change feet");
//
//					RS2Widget recolor = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 130);
//					if (recolor != null) {
//						recolor.interact("Recolour feet");
//					}
//				}
//			}
//			progress = CharacterCreationSectionProgress.ACCEPT_CHARACTER;
//		} else if (progress == CharacterCreationSectionProgress.ACCEPT_CHARACTER) {
//			RS2Widget widgetAcceptCharacter = getWidgets().get(DESIGN_CHARACTER_PARENT_ID, 100);
//			if (widgetAcceptCharacter.interact("Accept")) {
//				progress = CharacterCreationSectionProgress.TALK_WITH_NPC_1;
//			} else {
//				progress = CharacterCreationSectionProgress.CREATING_RANDOM_CHARACTER;
//			}
//		}

	}
}
