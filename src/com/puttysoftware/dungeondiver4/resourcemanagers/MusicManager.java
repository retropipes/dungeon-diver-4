/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.resourcemanagers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.retropipes.diane.asset.music.DianeMusicPlayer;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;

public class MusicManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/dungeondiver4/resources/music/";
    private static String LOAD_PATH = MusicManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = MusicManager.class;

    public static void playMusic(final String filename) {
	try {
	    final File modFile = new File(Dungeon.getDungeonTempFolder() + File.separator + "MusicTemp" + File.separator
		    + filename.toLowerCase() + ".mod");
	    if (!modFile.exists()) {
		final File modParent = modFile.getParentFile();
		if (!modParent.exists()) {
		    final boolean result = modParent.mkdirs();
		    if (!result) {
			throw new IOException();
		    }
		}
		try (final InputStream is = MusicManager.LOAD_CLASS
			.getResourceAsStream(MusicManager.LOAD_PATH + filename.toLowerCase() + ".mod")) {
		    DianeMusicPlayer.playStream(is);
		}
	    }
	} catch (final IOException io) {
	    DungeonDiver4.logError(io);
	}
    }

    public static void stopMusic() {
	DianeMusicPlayer.stopPlaying();
    }

    public static boolean isMusicPlaying() {
	return DianeMusicPlayer.isPlaying();
    }
}