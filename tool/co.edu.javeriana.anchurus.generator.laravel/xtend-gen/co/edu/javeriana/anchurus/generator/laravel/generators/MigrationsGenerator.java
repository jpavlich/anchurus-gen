package co.edu.javeriana.anchurus.generator.laravel.generators;

import co.edu.javeriana.anchurus.generator.laravel.AnchurusLaravelGenerator;
import co.edu.javeriana.anchurus.generator.laravel.templates.MigrationsTemplate;
import co.edu.javeriana.anchurus.generator.laravel.utils.UtilsAnchurus;
import co.edu.javeriana.isml.generator.common.SimpleGenerator;
import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Entity;
import com.google.inject.Inject;
import java.util.Calendar;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class MigrationsGenerator extends SimpleGenerator<Entity> {
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  @Inject
  @Extension
  private UtilsAnchurus _utilsAnchurus;
  
  @Override
  public String getOutputConfigurationName() {
    return AnchurusLaravelGenerator.MIGRATIONS;
  }
  
  @Override
  public SimpleTemplate<Entity> getTemplate() {
    return new MigrationsTemplate();
  }
  
  @Override
  protected String getFileName(final Entity e) {
    Calendar _fecha = this._utilsAnchurus.fecha();
    int _get = _fecha.get(Calendar.MONTH);
    int month = (_get + 1);
    Calendar _fecha_1 = this._utilsAnchurus.fecha();
    int day = _fecha_1.get(Calendar.DAY_OF_MONTH);
    Calendar _fecha_2 = this._utilsAnchurus.fecha();
    int hour = _fecha_2.get(Calendar.HOUR_OF_DAY);
    Calendar _fecha_3 = this._utilsAnchurus.fecha();
    int minute = _fecha_3.get(Calendar.MINUTE);
    Calendar _fecha_4 = this._utilsAnchurus.fecha();
    int second = _fecha_4.get(Calendar.SECOND);
    String strmon = "";
    String strday = "";
    String strhr = "";
    String strmin = "";
    String strsec = "";
    if ((month < 10)) {
      strmon = ("0" + Integer.valueOf(month));
    } else {
      String _plus = (Integer.valueOf(month) + "");
      strmon = _plus;
    }
    if ((day < 10)) {
      strday = ("0" + Integer.valueOf(day));
    } else {
      String _plus_1 = (Integer.valueOf(day) + "");
      strday = _plus_1;
    }
    if ((hour < 10)) {
      strhr = ("0" + Integer.valueOf(hour));
    } else {
      String _plus_2 = (Integer.valueOf(hour) + "");
      strhr = _plus_2;
    }
    if ((minute < 10)) {
      strmin = ("0" + Integer.valueOf(minute));
    } else {
      String _plus_3 = (Integer.valueOf(minute) + "");
      strmin = _plus_3;
    }
    if ((second < 10)) {
      strsec = ("0" + Integer.valueOf(second));
    } else {
      String _plus_4 = (Integer.valueOf(second) + "");
      strsec = _plus_4;
    }
    Calendar _fecha_5 = this._utilsAnchurus.fecha();
    int _get_1 = _fecha_5.get(Calendar.YEAR);
    String _plus_5 = (Integer.valueOf(_get_1) + "_");
    String _plus_6 = (_plus_5 + strmon);
    String _plus_7 = (_plus_6 + "_");
    String _plus_8 = (_plus_7 + strday);
    String _plus_9 = (_plus_8 + "_");
    String _plus_10 = (_plus_9 + strhr);
    String _plus_11 = (_plus_10 + strmin);
    String _plus_12 = (_plus_11 + strsec);
    String _plus_13 = (_plus_12 + "_create_");
    String _name = e.getName();
    String _snakeCase = this._utilsAnchurus.toSnakeCase(_name);
    String _plus_14 = (_plus_13 + _snakeCase);
    String _plus_15 = (_plus_14 + "s_table");
    return (_plus_15 + ".php");
  }
}
