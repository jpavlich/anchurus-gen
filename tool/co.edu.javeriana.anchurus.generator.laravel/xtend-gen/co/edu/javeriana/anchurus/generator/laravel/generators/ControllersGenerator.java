package co.edu.javeriana.anchurus.generator.laravel.generators;

import co.edu.javeriana.anchurus.generator.laravel.AnchurusLaravelGenerator;
import co.edu.javeriana.anchurus.generator.laravel.templates.ControllersTemplate;
import co.edu.javeriana.isml.generator.common.SimpleGenerator;
import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Controller;
import com.google.inject.Inject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class ControllersGenerator extends SimpleGenerator<Controller> {
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  @Override
  public String getOutputConfigurationName() {
    return AnchurusLaravelGenerator.CONTROLLERS;
  }
  
  @Override
  public SimpleTemplate<Controller> getTemplate() {
    return new ControllersTemplate();
  }
  
  @Override
  protected String getFileName(final Controller c) {
    String _name = c.getName();
    String _plus = (_name + "Controller");
    return (_plus + ".php");
  }
}
