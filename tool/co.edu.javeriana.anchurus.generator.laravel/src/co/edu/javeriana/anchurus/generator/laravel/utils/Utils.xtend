package co.edu.javeriana.anchurus.generator.laravel.utils

import java.util.Calendar
import com.google.common.base.CaseFormat

class Utils {
	def toSnakeCase(String string) {
		CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string)
	}
	def fecha(){
		return Calendar.getInstance()
	}
}