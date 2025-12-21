/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.desktop.QuitEvent;
import java.awt.desktop.QuitHandler;
import java.awt.desktop.QuitResponse;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.retropipes.diane.asset.image.BufferedImageIcon;
import org.retropipes.diane.fileio.utility.DirectoryUtilities;

import com.puttysoftware.dungeondiver4.creatures.party.PartyManager;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonManager;
import com.puttysoftware.dungeondiver4.prefs.PreferencesManager;
import com.puttysoftware.dungeondiver4.resourcemanagers.LogoManager;

public class GUIManager implements QuitHandler {
    // Fields
    private final JFrame guiFrame;
    private final JLabel logoLabel;

    // Constructors
    public GUIManager() {
	this.guiFrame = new JFrame("DungeonDiver4");
	final Container guiPane = this.guiFrame.getContentPane();
	this.guiFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	this.guiFrame.setLayout(new GridLayout(1, 1));
	this.logoLabel = new JLabel("", null, SwingConstants.CENTER);
	this.logoLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
	guiPane.add(this.logoLabel);
	this.guiFrame.setResizable(false);
    }

    // Methods
    public JFrame getGUIFrame() {
	if (this.guiFrame.isVisible()) {
	    return this.guiFrame;
	} else {
	    return null;
	}
    }

    public void showGUI() {
	final Application app = DungeonDiver4.getApplication();
	app.setInGUI();
	this.guiFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
	this.guiFrame.setVisible(true);
	app.getMenuManager().setMainMenus();
	app.getMenuManager().checkFlags();
    }

    public void hideGUI() {
	this.guiFrame.setVisible(false);
    }

    public void hideGUITemporarily() {
	this.guiFrame.setVisible(false);
    }

    public void updateLogo() {
	final BufferedImageIcon logo = LogoManager.getLogo();
	this.logoLabel.setIcon(logo);
	final Image iconlogo = DungeonDiver4.getApplication().getIconLogo();
	this.guiFrame.setIconImage(iconlogo);
	this.guiFrame.pack();
    }

    public boolean quitHandler() {
	final DungeonManager mm = DungeonDiver4.getApplication().getDungeonManager();
	boolean saved = true;
	int status = JOptionPane.DEFAULT_OPTION;
	if (mm.getDirty()) {
	    status = mm.showSaveDialog();
	    if (status == JOptionPane.YES_OPTION) {
		saved = mm.saveDungeon();
	    } else if (status == JOptionPane.CANCEL_OPTION) {
		saved = false;
	    } else {
		mm.setDirty(false);
	    }
	}
	if (saved) {
	    PreferencesManager.writePrefs();
	    if (PreferencesManager.areCharacterChangesPermanent()) {
		PartyManager.writebackCharacters();
	    }
	    // Run cleanup task
	    try {
		final File dirToDelete = new File(Dungeon.getDungeonTempFolder());
		DirectoryUtilities.removeDirectory(dirToDelete);
	    } catch (final Throwable t) {
		// Ignore
	    }
	}
	return saved;
    }

    @Override
    public void handleQuitRequestWith(QuitEvent e, QuitResponse response) {
	if (this.quitHandler()) {
	    response.performQuit();
	} else {
	    response.cancelQuit();
	}
    }
}
