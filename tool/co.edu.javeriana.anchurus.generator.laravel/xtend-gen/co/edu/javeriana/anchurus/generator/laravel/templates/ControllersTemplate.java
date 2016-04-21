package co.edu.javeriana.anchurus.generator.laravel.templates;

import co.edu.javeriana.anchurus.generator.laravel.utils.UtilsAnchurus;
import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Action;
import co.edu.javeriana.isml.isml.ActionCall;
import co.edu.javeriana.isml.isml.Assignment;
import co.edu.javeriana.isml.isml.Attribute;
import co.edu.javeriana.isml.isml.Controller;
import co.edu.javeriana.isml.isml.Entity;
import co.edu.javeriana.isml.isml.Expression;
import co.edu.javeriana.isml.isml.For;
import co.edu.javeriana.isml.isml.If;
import co.edu.javeriana.isml.isml.MethodCall;
import co.edu.javeriana.isml.isml.MethodStatement;
import co.edu.javeriana.isml.isml.NamedElement;
import co.edu.javeriana.isml.isml.Parameter;
import co.edu.javeriana.isml.isml.ParameterizedType;
import co.edu.javeriana.isml.isml.Reference;
import co.edu.javeriana.isml.isml.ResourceReference;
import co.edu.javeriana.isml.isml.Return;
import co.edu.javeriana.isml.isml.Service;
import co.edu.javeriana.isml.isml.Show;
import co.edu.javeriana.isml.isml.Type;
import co.edu.javeriana.isml.isml.TypeSpecification;
import co.edu.javeriana.isml.isml.TypedElement;
import co.edu.javeriana.isml.isml.Variable;
import co.edu.javeriana.isml.isml.VariableReference;
import co.edu.javeriana.isml.isml.ViewInstance;
import co.edu.javeriana.isml.isml.While;
import co.edu.javeriana.isml.scoping.IsmlModelNavigation;
import co.edu.javeriana.isml.validation.TypeChecker;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
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
  
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
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
        _builder.append("use App\\co\\edu\\javeriana\\");
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
    _builder.append("public function __construct(){");
    _builder.newLine();
    {
      EList<Action> _body_1 = c.getBody();
      Iterable<Attribute> _filter_1 = Iterables.<Attribute>filter(_body_1, Attribute.class);
      for(final Attribute attr_1 : _filter_1) {
        {
          Type _type = attr_1.getType();
          TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
          String _name_3 = _typeSpecification.getName();
          boolean _equalsIgnoreCase = _name_3.equalsIgnoreCase("persistence");
          if (_equalsIgnoreCase) {
            _builder.append("\t\t");
            _builder.append("$this->");
            String _name_4 = attr_1.getName();
            _builder.append(_name_4, "\t\t");
            _builder.append(" = new ");
            Type _type_1 = attr_1.getType();
            TypeSpecification _typeSpecification_1 = this._ismlModelNavigation.getTypeSpecification(_type_1);
            String _name_5 = _typeSpecification_1.getName();
            _builder.append(_name_5, "\t\t");
            _builder.append("(\'App\\");
            Type _type_2 = attr_1.getType();
            ParameterizedType _cast = this._ismlModelNavigation.<Type, ParameterizedType>cast(_type_2, ParameterizedType.class);
            EList<Type> _typeParameters = _cast.getTypeParameters();
            Type _get = _typeParameters.get(0);
            TypeSpecification _typeSpecification_2 = this._ismlModelNavigation.getTypeSpecification(_get);
            QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification_2);
            String _string = _fullyQualifiedName.toString("\\");
            _builder.append(_string, "\t\t");
            _builder.append("\');");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("\t\t");
            _builder.append("$this->");
            String _name_6 = attr_1.getName();
            _builder.append(_name_6, "\t\t");
            _builder.append(" = new ");
            Type _type_3 = attr_1.getType();
            TypeSpecification _typeSpecification_3 = this._ismlModelNavigation.getTypeSpecification(_type_3);
            String _name_7 = _typeSpecification_3.getName();
            _builder.append(_name_7, "\t\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.append("}");
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
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence generateFunction(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public function ");
    String _name = action.getName();
    _builder.append(_name, "");
    _builder.append("(Request $req ");
    {
      EList<Parameter> _parameters = action.getParameters();
      int _size = _parameters.size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        _builder.append(", ");
        CharSequence _parameters_1 = this.getParameters(action);
        _builder.append(_parameters_1, "");
      }
    }
    _builder.append("){");
    _builder.newLineIfNotEmpty();
    {
      EList<Parameter> _parameters_2 = action.getParameters();
      int _size_1 = _parameters_2.size();
      boolean _notEquals = (_size_1 != 0);
      if (_notEquals) {
        {
          EList<Parameter> _parameters_3 = action.getParameters();
          for(final Parameter param : _parameters_3) {
            _builder.append("\t");
            _builder.append("$");
            String _name_1 = param.getName();
            _builder.append(_name_1, "\t");
            _builder.append(" = NULL;");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<Parameter> _parameters_4 = action.getParameters();
          for(final Parameter param_1 : _parameters_4) {
            _builder.append("\t");
            _builder.append("if(is_numeric($");
            String _name_2 = param_1.getName();
            _builder.append(_name_2, "\t");
            _builder.append("_id)){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("$");
            String _name_3 = param_1.getName();
            _builder.append(_name_3, "\t\t");
            _builder.append(" = ");
            Type _type = param_1.getType();
            TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
            String _name_4 = _typeSpecification.getName();
            _builder.append(_name_4, "\t\t");
            _builder.append("::find($");
            String _name_5 = param_1.getName();
            _builder.append(_name_5, "\t\t");
            _builder.append("_id);");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("$");
            String _name_6 = param_1.getName();
            _builder.append(_name_6, "\t\t");
            _builder.append("->update($req->all());");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t");
    EList<MethodStatement> _body = action.getBody();
    CharSequence _generateBody = this.generateBody(_body);
    _builder.append(_generateBody, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generateBody(final List<MethodStatement> lms) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final MethodStatement sentence : lms) {
        {
          boolean _or = false;
          if ((sentence instanceof If)) {
            _or = true;
          } else {
            _or = (sentence instanceof For);
          }
          if (_or) {
            CharSequence _generateMethodStatement = this.generateMethodStatement(sentence);
            _builder.append(_generateMethodStatement, "");
          } else {
            CharSequence _generateMethodStatement_1 = this.generateMethodStatement(sentence);
            _builder.append(_generateMethodStatement_1, "");
            _builder.append(";");
          }
        }
      }
    }
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateMethodStatement(final If ifst) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if(");
    Expression _condition = ifst.getCondition();
    CharSequence _valueTemplate = this._utilsAnchurus.valueTemplate(_condition);
    _builder.append(_valueTemplate, "");
    _builder.append("){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    EList<MethodStatement> _body = ifst.getBody();
    CharSequence _generateBody = this.generateBody(_body);
    _builder.append(_generateBody, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    {
      EList<MethodStatement> _elseBody = ifst.getElseBody();
      int _size = 0;
      if (_elseBody!=null) {
        _size=_elseBody.size();
      }
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        _builder.append("else{");
        _builder.newLine();
        _builder.append("\t");
        EList<MethodStatement> _elseBody_1 = ifst.getElseBody();
        CharSequence _generateBody_1 = this.generateBody(_elseBody_1);
        _builder.append(_generateBody_1, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence _generateMethodStatement(final Assignment assign) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence _generateMethodStatement(final Return returnst) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence _generateMethodStatement(final For forst) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("foreach(");
    Reference _collection = forst.getCollection();
    CharSequence _valueTemplate = this._utilsAnchurus.valueTemplate(_collection);
    _builder.append(_valueTemplate, "");
    _builder.append(" as &");
    Variable _variable = forst.getVariable();
    CharSequence _generateMethodStatement = this.generateMethodStatement(_variable);
    _builder.append(_generateMethodStatement, "");
    _builder.append("){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    EList<MethodStatement> _body = forst.getBody();
    CharSequence _generateBody = this.generateBody(_body);
    _builder.append(_generateBody, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _generateMethodStatement(final While whilest) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence _generateMethodStatement(final ActionCall acst) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("$this->");
    Action _action = this._ismlModelNavigation.getAction(acst);
    String _name = _action.getName();
    _builder.append(_name, "");
    _builder.append("($req");
    {
      Action _action_1 = this._ismlModelNavigation.getAction(acst);
      EList<Parameter> _parameters = _action_1.getParameters();
      int _size = _parameters.size();
      boolean _notEquals = (_size != 0);
      if (_notEquals) {
        _builder.append(", ");
        Action _action_2 = this._ismlModelNavigation.getAction(acst);
        CharSequence _parameters_1 = this.getParameters(_action_2);
        _builder.append(_parameters_1, "");
      }
    }
    _builder.append(")");
    return _builder;
  }
  
  protected CharSequence _generateMethodStatement(final MethodCall mcst) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence _generateMethodStatement(final ResourceReference resref) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence _generateMethodStatement(final Type type) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence _generateMethodStatement(final VariableReference vr) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _valueTemplate = this._utilsAnchurus.valueTemplate(vr);
    _builder.append(_valueTemplate, "");
    return _builder;
  }
  
  protected CharSequence _generateMethodStatement(final Variable variable) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("$");
    String _name = variable.getName();
    _builder.append(_name, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateMethodStatement(final Show show) {
    CharSequence _xblockexpression = null;
    {
      final Expression expression = show.getExpression();
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
          _builder.append(")");
          _switchResult = _builder;
        }
      }
      if (!_matched) {
        StringConcatenation _builder = new StringConcatenation();
        _switchResult = _builder;
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public CharSequence getParameters(final Action action) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Parameter> _parameters = action.getParameters();
      int _size = _parameters.size();
      boolean _notEquals = (_size != 0);
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
            CharSequence _generateParams = this.generateParams(param);
            _builder.append(_generateParams, "");
          }
        }
      } else {
      }
    }
    return _builder;
  }
  
  public CharSequence generateParams(final Parameter p) {
    Type _type = p.getType();
    TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
    if ((_typeSpecification instanceof Entity)) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("$");
      String _name = p.getName();
      _builder.append(_name, "");
      _builder.append("_id");
      return _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("$");
      String _name_1 = p.getName();
      _builder_1.append(_name_1, "");
      return _builder_1;
    }
  }
  
  public CharSequence generateMethodStatement(final MethodStatement acst) {
    if (acst instanceof ActionCall) {
      return _generateMethodStatement((ActionCall)acst);
    } else if (acst instanceof MethodCall) {
      return _generateMethodStatement((MethodCall)acst);
    } else if (acst instanceof For) {
      return _generateMethodStatement((For)acst);
    } else if (acst instanceof If) {
      return _generateMethodStatement((If)acst);
    } else if (acst instanceof ResourceReference) {
      return _generateMethodStatement((ResourceReference)acst);
    } else if (acst instanceof Type) {
      return _generateMethodStatement((Type)acst);
    } else if (acst instanceof Variable) {
      return _generateMethodStatement((Variable)acst);
    } else if (acst instanceof VariableReference) {
      return _generateMethodStatement((VariableReference)acst);
    } else if (acst instanceof While) {
      return _generateMethodStatement((While)acst);
    } else if (acst instanceof Assignment) {
      return _generateMethodStatement((Assignment)acst);
    } else if (acst instanceof Return) {
      return _generateMethodStatement((Return)acst);
    } else if (acst instanceof Show) {
      return _generateMethodStatement((Show)acst);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(acst).toString());
    }
  }
}
