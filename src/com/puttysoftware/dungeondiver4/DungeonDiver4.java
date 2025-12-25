/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4;

import org.retropipes.diane.Diane;
import org.retropipes.diane.gui.dialog.CommonDialogs;
import org.retropipes.diane.integration.Integration;

import com.puttysoftware.dungeondiver4.creatures.AbstractCreature;
import com.puttysoftware.dungeondiver4.prefs.PreferencesLauncher;

public class DungeonDiver4 {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "DungeonDiver4";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
	    + "Include the error log with your bug report.\n" + "Email bug reports to: products@puttysoftware.com\n"
	    + "Subject: DungeonDiver4 Bug Report";
    private static final String ERROR_TITLE = "DungeonDiver4 Error";
    private static final int BATTLE_DUNGEON_SIZE = 16;
    private static final boolean DEBUG_MODE = false;

    // Methods
    public static Application getApplication() {
	return DungeonDiver4.application;
    }

    public static void logError(final Throwable t) {
	String suffix;
	if (DungeonDiver4.inDebugMode()) {
	    suffix = " (DEBUG)";
	} else {
	    suffix = "";
	}
	// Display error message
	CommonDialogs.showErrorDialog(DungeonDiver4.ERROR_MESSAGE, DungeonDiver4.ERROR_TITLE + suffix);
	Diane.handleError(t);
    }

    public static boolean inDebugMode() {
	return DungeonDiver4.DEBUG_MODE;
    }

    public static int getBattleDungeonSize() {
	return DungeonDiver4.BATTLE_DUNGEON_SIZE;
    }

    public static void preInit() {
	// Compute action cap
	AbstractCreature.computeActionCap(DungeonDiver4.BATTLE_DUNGEON_SIZE, DungeonDiver4.BATTLE_DUNGEON_SIZE);
    }

    public static void main(final String[] args) {
	try {
	    // Pre-Init
	    DungeonDiver4.preInit();
	    // Integrate with host platform
	    Integration i = Integration.integrate();
	    DungeonDiver4.application = new Application();
	    DungeonDiver4.application.postConstruct();
	    DungeonDiver4.application.playLogoSound();
	    DungeonDiver4.application.getGUIManager().showGUI();
	    // Register platform hooks
	    i.setAboutHandler(DungeonDiver4.application.getAboutDialog());
	    i.setOpenFileHandler(DungeonDiver4.application.getDungeonManager());
	    i.setPreferencesHandler(new PreferencesLauncher());
	    i.setQuitHandler(DungeonDiver4.application.getGUIManager());
	    // Set up Common Dialogs
	    CommonDialogs.setDefaultTitle(DungeonDiver4.PROGRAM_NAME);
	    CommonDialogs.setIcon(DungeonDiver4.application.getMicroLogo());
	} catch (final Throwable t) {
	    DungeonDiver4.logError(t);
	}
    }
}
