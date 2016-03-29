package co.edu.javeriana.anchurus.generator.laravel.templates;

import co.edu.javeriana.anchurus.generator.laravel.utils.UtilsAnchurus;
import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Action;
import co.edu.javeriana.isml.isml.Attribute;
import co.edu.javeriana.isml.isml.Controller;
import co.edu.javeriana.isml.isml.Entity;
import co.edu.javeriana.isml.isml.Expression;
import co.edu.javeriana.isml.isml.MethodStatement;
import co.edu.javeriana.isml.isml.NamedElement;
import co.edu.javeriana.isml.isml.Parameter;
import co.edu.javeriana.isml.isml.Service;
import co.edu.javeriana.isml.isml.Show;
import co.edu.javeriana.isml.isml.Type;
import co.edu.javeriana.isml.isml.TypeSpecification;
import co.edu.javeriana.isml.isml.TypedElement;
import co.edu.javeriana.isml.isml.ViewInstance;
import co.edu.javeriana.isml.scoping.IsmlModelNavigation;
import co.edu.javeriana.isml.validation.TypeChecker;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class ControllersTemplate extends SimpleTemplate<Controller> {
  @Inject
  @Extension
  private TypeChecker _typeChecker;
  
  @Inject
  @Extension
  private IsmlModelNavigation _ismlModelNavigation;
  
  @Inject
  @Extension
  private UtilsAnchurus _utilsAnchurus;
  
  private List<TypedElement> imports = new ArrayList<TypedElement>();
  
  private Set<Entity> entitySubGroup = new LinkedHashSet<Entity>();
  
  private Set<Service> controllerSG = new LinkedHashSet<Service>();
  
  @Override
  public void preprocess(final Controller c) {
    TreeIterator<EObject> _eAllContents = c.eAllContents();
    Iterator<TypedElement> _filter = Iterators.<TypedElement>filter(_eAllContents, TypedElement.class);
    final List<TypedElement> descendants = IteratorExtensions.<TypedElement>toList(_filter);
    this.imports.addAll(descendants);
    for (final TypedElement desc : descendants) {
      {
        Type _type = desc.getType();
        TypeSpecification _typeSpecification = null;
        if (_type!=null) {
          _typeSpecification=this._ismlModelNavigation.getTypeSpecification(_type);
        }
        final NamedElement e = _typeSpecification;
        if ((e instanceof Entity)) {
          this.entitySubGroup.add(((Entity)e));
        } else {
          if ((e instanceof Service)) {
            this.controllerSG.add(((Service)e));
          }
        }
      }
    }
  }
  
  @Override
  public CharSequence template(final Controller c) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?php");
    _builder.newLine();
    _builder.append("namespace App\\Http\\Controllers;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("use Input;");
    _builder.newLine();
    _builder.append("use Redirect;");
    _builder.newLine();
    _builder.append("use Illuminate\\Http\\Request;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("use App\\Http\\Requests;");
    _builder.newLine();
    _builder.append("use App\\Http\\Controllers\\Controller;");
    _builder.newLine();
    {
      for(final Entity elm : this.entitySubGroup) {
        _builder.append("use App\\");
        String _name = elm.getName();
        _builder.append(_name, "");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      for(final Service elm_1 : this.controllerSG) {
        _builder.append("use App\\Http\\Controllers\\");
        String _name_1 = elm_1.getName();
        _builder.append(_name_1, "");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("class ");
    String _name_2 = c.getName();
    _builder.append(_name_2, "");
    _builder.append("Controller extends Controller{");
    _builder.newLineIfNotEmpty();
    {
      EList<Action> _body = c.getBody();
      Iterable<Attribute> _filter = Iterables.<Attribute>filter(_body, Attribute.class);
      for(final Attribute attr : _filter) {
        _builder.append("\t");
        CharSequence _generateAttributes = this.generateAttributes(attr);
        _builder.append(_generateAttributes, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      Iterable<Action> _actions = this._ismlModelNavigation.getActions(c);
      for(final Action func : _actions) {
        _builder.append("\t");
        CharSequence _generateFunction = this.generateFunction(func);
        _builder.append(_generateFunction, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generateAttributes(final Attribute attribute) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("private $");
    String _name = attribute.getName();
    _builder.append(_name, "");
    _builder.append(" = new ");
    Type _type = attribute.getType();
    TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
    String _name_1 = _typeSpecification.getName();
    _builder.append(_name_1, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence generateFunction(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public function ");
    String _name = action.getName();
    _builder.append(_name, "");
    _builder.append("(");
    CharSequence _parameters = this.getParameters(action);
    _builder.append(_parameters, "");
    _builder.append("){");
    _builder.newLineIfNotEmpty();
    {
      EList<MethodStatement> _body = action.getBody();
      for(final MethodStatement sentence : _body) {
        _builder.append("\t");
        CharSequence _generateBody = this.generateBody(sentence);
        _builder.append(_generateBody, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generateBody(final MethodStatement statement) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (statement instanceof Show) {
        _matched=true;
        Expression _expression = ((Show)statement).getExpression();
        _switchResult = this.getPage(_expression);
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _switchResult = _builder;
    }
    return _switchResult;
  }
  
  public CharSequence getPage(final Expression expression) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (expression instanceof ViewInstance) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("return view(\'co.edu.javeriana.");
        Type _type = ((ViewInstance)expression).getType();
        TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
        String _name = _typeSpecification.getName();
        String _snakeCase = this._utilsAnchurus.toSnakeCase(_name);
        _builder.append(_snakeCase, "");
        _builder.append("\', ");
        CharSequence _generateArray = this._utilsAnchurus.generateArray(((ViewInstance)expression));
        _builder.append(_generateArray, "");
        _builder.append("); ");
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _switchResult = _builder;
    }
    return _switchResult;
  }
  
  public CharSequence getParameters(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Parameter> _parameters = action.getParameters();
      boolean _notEquals = (!Objects.equal(_parameters, Integer.valueOf(0)));
      if (_notEquals) {
        {
          EList<Parameter> _parameters_1 = action.getParameters();
          boolean _hasElements = false;
          for(final Parameter param : _parameters_1) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(",", "");
            }
            _builder.append("$");
            String _name = param.getName();
            _builder.append(_name, "");
          }
        }
      } else {
      }
    }
    return _builder;
  }
}
