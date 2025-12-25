/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.editor.rulesets;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.retropipes.diane.fileio.DataIOFactory;
import org.retropipes.diane.fileio.XDataReader;
import org.retropipes.diane.gui.dialog.CommonDialogs;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;

public class RuleSetLoadTask extends Thread {
    // Fields
    private final String filename;

    // Constructors
    public RuleSetLoadTask(final String file) {
	this.filename = file;
	this.setName("Rule Set File Reader");
    }

    // Methods
    @Override
    public void run() {
	final Application app = DungeonDiver4.getApplication();
	final String sg = "Rule Set";
	try (XDataReader ruleSetFile = DataIOFactory.createTagReader(this.filename, "ruleset")) {
	    final int magic = ruleSetFile.readInt();
	    if (magic == RuleSetConstants.MAGIC_NUMBER_2) {
		// Format 2 file
		app.getObjects().readRuleSet(ruleSetFile, RuleSetConstants.FORMAT_2);
	    }
	    CommonDialogs.showTitledDialog(sg + " file loaded.", "Rule Set Picker");
	} catch (final FileNotFoundException fnfe) {
	    CommonDialogs.showDialog("Loading the " + sg.toLowerCase()
		    + " file failed, probably due to illegal characters in the file name.");
	    app.getDungeonManager().handleDeferredSuccess(false);
	} catch (final IOException ie) {
	    CommonDialogs.showDialog(ie.getMessage());
	    app.getDungeonManager().handleDeferredSuccess(false);
	} catch (final Exception ex) {
	    DungeonDiver4.logError(ex);
	}
    }
}
