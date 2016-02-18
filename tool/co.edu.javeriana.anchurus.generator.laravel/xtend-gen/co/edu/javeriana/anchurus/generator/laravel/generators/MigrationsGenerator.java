package co.edu.javeriana.anchurus.generator.laravel.generators;

import co.edu.javeriana.anchurus.generator.laravel.AnchurusLaravelGenerator;
import co.edu.javeriana.anchurus.generator.laravel.templates.MigrationsTemplate;
import co.edu.javeriana.anchurus.generator.laravel.utils.Utils;
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
  private Utils _utils;
  
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
    Calendar _fecha = this._utils.fecha();
    int _get = _fecha.get(Calendar.YEAR);
    String _plus = (Integer.valueOf(_get) + "_");
    Calendar _fecha_1 = this._utils.fecha();
    int _get_1 = _fecha_1.get(Calendar.MONTH);
    int _plus_1 = (_get_1 + 1);
    String _plus_2 = (_plus + Integer.valueOf(_plus_1));
    String _plus_3 = (_plus_2 + "_");
    Calendar _fecha_2 = this._utils.fecha();
    int _get_2 = _fecha_2.get(Calendar.DAY_OF_MONTH);
    String _plus_4 = (_plus_3 + Integer.valueOf(_get_2));
    String _plus_5 = (_plus_4 + "_");
    Calendar _fecha_3 = this._utils.fecha();
    int _get_3 = _fecha_3.get(Calendar.HOUR_OF_DAY);
    String _plus_6 = (_plus_5 + Integer.valueOf(_get_3));
    Calendar _fecha_4 = this._utils.fecha();
    int _get_4 = _fecha_4.get(Calendar.MINUTE);
    String _plus_7 = (_plus_6 + Integer.valueOf(_get_4));
    Calendar _fecha_5 = this._utils.fecha();
    int _get_5 = _fecha_5.get(Calendar.SECOND);
    String _plus_8 = (_plus_7 + Integer.valueOf(_get_5));
    String _plus_9 = (_plus_8 + "_create_");
    String _name = e.getName();
    String _snakeCase = this._utils.toSnakeCase(_name);
    String _plus_10 = (_plus_9 + _snakeCase);
    String _plus_11 = (_plus_10 + "_table");
    return (_plus_11 + ".php");
  }
}
