/*
 * generated by Xtext 2.9.1
 */
package org.xtext.example.mydsl.idea.completion

import org.eclipse.xtext.idea.lang.AbstractXtextLanguage
import org.xtext.example.mydsl.idea.lang.MyDslLanguage

class MyDslCompletionContributor extends AbstractMyDslCompletionContributor {
	new() {
		this(MyDslLanguage.INSTANCE)
	}
	
	new(AbstractXtextLanguage lang) {
		super(lang)
		//custom rules here
	}
}
