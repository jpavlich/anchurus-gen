package co.edu.javeriana.anchurus.generator.laravel.generators;

import co.edu.javeriana.anchurus.generator.laravel.AnchurusLaravelGenerator;
import co.edu.javeriana.anchurus.generator.laravel.templates.ModelsTemplate;
import co.edu.javeriana.isml.generator.common.SimpleGenerator;
import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Entity;
import com.google.inject.Inject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class ModelsGenerator extends SimpleGenerator<Entity> {
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  @Override
  public String getOutputConfigurationName() {
    return AnchurusLaravelGenerator.MODELS;
  }
  
  @Override
  public SimpleTemplate<Entity> getTemplate() {
    return new ModelsTemplate();
  }
  
  @Override
  protected String getFileName(final Entity e) {
    EObject _eContainer = e.eContainer();
    QualifiedName _fullyQualifiedName = null;
    if (_eContainer!=null) {
      _fullyQualifiedName=this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer);
    }
    String _string = _fullyQualifiedName.toString("/");
    String _plus = (_string + "/");
    String _name = e.getName();
    String _plus_1 = (_plus + _name);
    return (_plus_1 + ".php");
  }
}
