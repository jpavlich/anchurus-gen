/*
 * generated by Xtext 2.9.1
 */
package org.xtext.example.mydsl.idea.highlighting;

import org.eclipse.xtext.idea.highlighting.AbstractColorSettingsPage;
import org.xtext.example.mydsl.idea.lang.MyDslLanguage;

public class MyDslBaseColorSettingsPage extends AbstractColorSettingsPage {
	
	public MyDslBaseColorSettingsPage() {
		MyDslLanguage.INSTANCE.injectMembers(this);
	}

	@Override
	public String getDisplayName() {
		return MyDslLanguage.INSTANCE.getDisplayName();
	}
}