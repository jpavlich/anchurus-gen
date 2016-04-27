package co.edu.javeriana.anchurus.generator.laravel.templates;

import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Entity;
import co.edu.javeriana.isml.scoping.IsmlModelNavigation;
import co.edu.javeriana.isml.validation.TypeChecker;
import com.google.inject.Inject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class ModelsTemplate extends SimpleTemplate<Entity> {
  @Inject
  @Extension
  private TypeChecker _typeChecker;
  
  @Inject
  @Extension
  private IsmlModelNavigation _ismlModelNavigation;
  
  @Override
  public void preprocess(final Entity e) {
  }
  
  /**
   * This method makes the PHP Model archive for a given ISML entity.
   * @param e the Entity
   * @return the PHP Model archive
   */
  @Override
  public CharSequence template(final Entity e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?php");
    _builder.newLine();
    _builder.append("namespace App\\co\\edu\\javeriana;");
    _builder.newLine();
    _builder.append("use Illuminate\\Database\\Eloquent\\Model;\t");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class ");
    String _name = e.getName();
    _builder.append(_name, "");
    _builder.append(" extends Model{");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("protected $guarded = [];");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    return _builder;
  }
}
