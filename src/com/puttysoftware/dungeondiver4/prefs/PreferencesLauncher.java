package com.puttysoftware.dungeondiver4.prefs;

import java.awt.desktop.PreferencesEvent;
import java.awt.desktop.PreferencesHandler;

public class PreferencesLauncher implements PreferencesHandler {
    @Override
    public void handlePreferences(PreferencesEvent e) {
	PreferencesManager.showPrefs();
    }
}
