package co.edu.javeriana.anchurus.generator.laravel.templates;

import co.edu.javeriana.anchurus.generator.laravel.utils.Utils;
import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Action;
import co.edu.javeriana.isml.isml.Controller;
import co.edu.javeriana.isml.isml.Expression;
import co.edu.javeriana.isml.isml.MethodStatement;
import co.edu.javeriana.isml.isml.Parameter;
import co.edu.javeriana.isml.isml.Show;
import co.edu.javeriana.isml.isml.Type;
import co.edu.javeriana.isml.isml.TypeSpecification;
import co.edu.javeriana.isml.isml.ViewInstance;
import co.edu.javeriana.isml.scoping.IsmlModelNavigation;
import co.edu.javeriana.isml.validation.TypeChecker;
import com.google.common.base.Objects;
import com.google.inject.Inject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;

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
  private Utils _utils;
  
  private int i;
  
  @Override
  public void preprocess(final Controller c) {
    this.i = 1;
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
    _builder.append("use App\\Project;");
    _builder.newLine();
    _builder.append("use App\\Http\\Requests;");
    _builder.newLine();
    _builder.append("use App\\Http\\Controllers\\Controller;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class ");
    String _name = c.getName();
    _builder.append(_name, "");
    _builder.append("Controller extends Controller{");
    _builder.newLineIfNotEmpty();
    {
      Iterable<Action> _actions = this._ismlModelNavigation.getActions(c);
      for(final Action func : _actions) {
        _builder.append("\t");
        CharSequence _generarFuncionalidad = this.generarFuncionalidad(func);
        _builder.append(_generarFuncionalidad, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generarFuncionalidad(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public function ");
    String _name = action.getName();
    _builder.append(_name, "");
    _builder.append("(");
    CharSequence _obtenerParametros = this.obtenerParametros(action);
    _builder.append(_obtenerParametros, "");
    _builder.append("){");
    _builder.newLineIfNotEmpty();
    {
      EList<MethodStatement> _body = action.getBody();
      for(final MethodStatement sentencia : _body) {
        _builder.append("\t");
        CharSequence _generarCuerpo = this.generarCuerpo(sentencia);
        _builder.append(_generarCuerpo, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generarCuerpo(final MethodStatement statement) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (statement instanceof Show) {
        _matched=true;
        Expression _expression = ((Show)statement).getExpression();
        _switchResult = this.obtenerPagina(_expression);
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _switchResult = _builder;
    }
    return _switchResult;
  }
  
  public CharSequence obtenerPagina(final Expression expression) {
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
        String _snakeCase = this._utils.toSnakeCase(_name);
        _builder.append(_snakeCase, "");
        _builder.append("\', ");
        CharSequence _generateArray = this._utils.generateArray(((ViewInstance)expression));
        _builder.append(_generateArray, "");
        _builder.append(") ");
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _switchResult = _builder;
    }
    return _switchResult;
  }
  
  public CharSequence obtenerParametros(final Action action) {
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
