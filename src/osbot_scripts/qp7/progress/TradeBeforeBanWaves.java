package osbot_scripts.qp7.progress;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.osbot.rs07.script.MethodProvider;

import osbot_scripts.config.Config;
import osbot_scripts.database.DatabaseUtilities;
import osbot_scripts.framework.GrandExchangeTask;
import osbot_scripts.framework.parts.BankItem;

public class TradeBeforeBanWaves {

	public static boolean MULE_TRADING_BEFORE_BANWAVE = false;

	public static void trade2(QuestStep quest) {
		new Thread(() -> {
			String timeToStart = "09:50:00";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
			SimpleDateFormat formatOnlyDay = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			Date dateToStart = null;

			Date tomorrow = new Date();
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Amsterdam"));

			try {
				dateToStart = format.parse(formatOnlyDay.format(now) + " at " + timeToStart);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			while (true) {
				Date atTheMoment = new Date();
				long diff = dateToStart.getTime() - atTheMoment.getTime();

				System.out.println("diff 1: " + diff);
				if (diff < 0 && diff > -600_000) {

					Ge2 ge = new Ge2(quest.getEvent(), quest);
					boolean iron = Ge2.isIronMiner((MethodProvider)quest);

					if (!Config.TRADE_OVER_CLAY || iron) {
						GrandExchangeTask task = new GrandExchangeTask(quest, new BankItem[] {},
								new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
										new BankItem("Uncut diamond", 1617, 1000, 1, true),
										new BankItem("Uncut emerald", 1621, 1000, 1, true),
										new BankItem("Uncut ruby", 1619, 1000, 1, true),
										new BankItem("Uncut sapphire", 1623, 1000, 1, true),
										new BankItem("Clay", 434, 1000, 1, true) },
								quest.getEvent(), quest.getScript(), quest);

						ge.setTask(task);
					}

					if (quest instanceof MiningLevelTo15Configuration) {
						MULE_TRADING_BEFORE_BANWAVE = true;
						((MiningLevelTo15Configuration) quest).setGrandExchangeActions(ge);
					}

					quest.log("going to the g.e. to sell everything before muling!");

					// DatabaseUtilities.updateAtASpecificTimeToMule();

					c.setTime(tomorrow);
					c.add(Calendar.DATE, 1);
					tomorrow = c.getTime();
					try {
						dateToStart = format.parse(formatOnlyDay.format(tomorrow) + " at " + timeToStart);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("end: " + diff);
				}

				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static void trade1(QuestStep quest) {
		new Thread(() -> {
			String timeToStart = "15:20:00";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
			SimpleDateFormat formatOnlyDay = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			Date dateToStart = null;

			Date tomorrow = new Date();
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Europe/Amsterdam"));

			try {
				dateToStart = format.parse(formatOnlyDay.format(now) + " at " + timeToStart);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			while (true) {
				Date atTheMoment = new Date();
				long diff = dateToStart.getTime() - atTheMoment.getTime();

				System.out.println("diff 1: " + diff);
				if (diff < 0 && diff > -600_000) {
					Ge2 ge = new Ge2(quest.getEvent(), quest);

					boolean iron = Ge2.isIronMiner((MethodProvider)quest);
					
					if (!Config.TRADE_OVER_CLAY || iron) {
						GrandExchangeTask task = new GrandExchangeTask(quest, new BankItem[] {},
								new BankItem[] { new BankItem("Iron ore", 440, 1000, 1, true),
										new BankItem("Uncut diamond", 1617, 1000, 1, true),
										new BankItem("Uncut emerald", 1621, 1000, 1, true),
										new BankItem("Uncut ruby", 1619, 1000, 1, true),
										new BankItem("Uncut sapphire", 1623, 1000, 1, true),
										new BankItem("Clay", 434, 1000, 1, true) },
								quest.getEvent(), quest.getScript(), quest);

						ge.setTask(task);
					}

					if (quest instanceof MiningLevelTo15Configuration) {
						MULE_TRADING_BEFORE_BANWAVE = true;
						((MiningLevelTo15Configuration) quest).setGrandExchangeActions(ge);
					}

					quest.log("going to the g.e. to sell everything before muling!");

					// DatabaseUtilities.updateAtASpecificTimeToMule();

					c.setTime(tomorrow);
					c.add(Calendar.DATE, 1);
					tomorrow = c.getTime();
					try {
						dateToStart = format.parse(formatOnlyDay.format(tomorrow) + " at " + timeToStart);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("end: " + diff);
				}

				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
