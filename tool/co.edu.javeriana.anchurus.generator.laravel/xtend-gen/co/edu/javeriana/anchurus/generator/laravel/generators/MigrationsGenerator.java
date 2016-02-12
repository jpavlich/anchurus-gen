package co.edu.javeriana.anchurus.generator.laravel.generators;

import co.edu.javeriana.anchurus.generator.laravel.AnchurusLaravelGenerator;
import co.edu.javeriana.anchurus.generator.laravel.templates.MigrationsTemplate;
import co.edu.javeriana.isml.generator.common.SimpleGenerator;
import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Entity;
import com.google.inject.Inject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class MigrationsGenerator extends SimpleGenerator<Entity> {
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  @Override
  public String getOutputConfigurationName() {
    return AnchurusLaravelGenerator.PAGES;
  }
  
  @Override
  public SimpleTemplate<Entity> getTemplate() {
    return new MigrationsTemplate();
  }
  
  @Override
  protected String getFileName(final Entity e) {
    String _name = e.getName();
    return (_name + ".php");
  }
}
