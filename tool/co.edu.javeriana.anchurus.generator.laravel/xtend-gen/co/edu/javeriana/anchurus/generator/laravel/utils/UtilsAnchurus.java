package co.edu.javeriana.anchurus.generator.laravel.utils;

import co.edu.javeriana.isml.isml.Action;
import co.edu.javeriana.isml.isml.ActionCall;
import co.edu.javeriana.isml.isml.Attribute;
import co.edu.javeriana.isml.isml.BoolValue;
import co.edu.javeriana.isml.isml.Controller;
import co.edu.javeriana.isml.isml.Entity;
import co.edu.javeriana.isml.isml.Expression;
import co.edu.javeriana.isml.isml.FloatValue;
import co.edu.javeriana.isml.isml.IntValue;
import co.edu.javeriana.isml.isml.Method;
import co.edu.javeriana.isml.isml.MethodCall;
import co.edu.javeriana.isml.isml.NamedElement;
import co.edu.javeriana.isml.isml.NullValue;
import co.edu.javeriana.isml.isml.Parameter;
import co.edu.javeriana.isml.isml.ParameterizedReference;
import co.edu.javeriana.isml.isml.Reference;
import co.edu.javeriana.isml.isml.StringValue;
import co.edu.javeriana.isml.isml.StructInstance;
import co.edu.javeriana.isml.isml.Type;
import co.edu.javeriana.isml.isml.TypeSpecification;
import co.edu.javeriana.isml.isml.Variable;
import co.edu.javeriana.isml.isml.VariableReference;
import co.edu.javeriana.isml.isml.VariableTypeElement;
import co.edu.javeriana.isml.isml.ViewInstance;
import co.edu.javeriana.isml.scoping.IsmlModelNavigation;
import com.google.common.base.CaseFormat;
import com.google.common.base.Objects;
import com.google.inject.Inject;
import java.util.Calendar;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class UtilsAnchurus {
  @Inject
  @Extension
  private IsmlModelNavigation _ismlModelNavigation;
  
  public String toSnakeCase(final String string) {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string);
  }
  
  public String toKebabCase(final String string) {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, string);
  }
  
  public Calendar fecha() {
    return Calendar.getInstance();
  }
  
  public CharSequence valueTemplate(final Expression e) {
    CharSequence _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (e instanceof BoolValue) {
        _matched=true;
        Object _literal = ((BoolValue)e).getLiteral();
        _switchResult = _literal.toString();
      }
    }
    if (!_matched) {
      if (e instanceof FloatValue) {
        _matched=true;
        Object _literal = ((FloatValue)e).getLiteral();
        _switchResult = _literal.toString();
      }
    }
    if (!_matched) {
      if (e instanceof IntValue) {
        _matched=true;
        Object _literal = ((IntValue)e).getLiteral();
        _switchResult = _literal.toString();
      }
    }
    if (!_matched) {
      if (e instanceof NullValue) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("NULL");
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (e instanceof StringValue) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(" ");
        _builder.append("\'");
        Object _literal = ((StringValue)e).getLiteral();
        String _string = _literal.toString();
        _builder.append(_string, " ");
        _builder.append("\' ");
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (e instanceof MethodCall) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("call");
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (e instanceof VariableReference) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        {
          boolean _hasTail = this.hasTail(((Reference)e));
          if (_hasTail) {
            CharSequence _generateTailedElement = this.generateTailedElement(((VariableReference)e));
            _builder.append(_generateTailedElement, "");
          } else {
            _builder.append("$");
            VariableTypeElement _referencedElement = ((VariableReference)e).getReferencedElement();
            String _name = _referencedElement.getName();
            _builder.append(_name, "");
          }
        }
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (e instanceof ViewInstance) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("return view(\'co.edu.javeriana.");
        Type _type = ((ViewInstance)e).getType();
        TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
        String _name = _typeSpecification.getName();
        String _snakeCase = this.toSnakeCase(_name);
        _builder.append(_snakeCase, "");
        _builder.append("\', ");
        CharSequence _generateArray = this.generateArray(((ViewInstance)e));
        _builder.append(_generateArray, "");
        _builder.append(") ");
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (e instanceof StructInstance) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("new ");
        Type _type = ((StructInstance)e).getType();
        TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
        String _name = _typeSpecification.getName();
        _builder.append(_name, "");
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      _switchResult = e.toString();
    }
    return _switchResult;
  }
  
  public CharSequence generateTailedElement(final VariableReference vr) {
    StringConcatenation _builder = new StringConcatenation();
    String accumulate = _builder.toString();
    boolean _or = false;
    VariableTypeElement _referencedElement = vr.getReferencedElement();
    if ((_referencedElement instanceof Attribute)) {
      _or = true;
    } else {
      VariableTypeElement _referencedElement_1 = vr.getReferencedElement();
      _or = (_referencedElement_1 instanceof Method);
    }
    if (_or) {
      String _accumulate = accumulate;
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("$this->");
      accumulate = (_accumulate + _builder_1);
    } else {
      String _accumulate_1 = accumulate;
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("$");
      accumulate = (_accumulate_1 + _builder_2);
    }
    CharSequence _generateReferencedElement = this.generateReferencedElement(vr);
    String str = _generateReferencedElement.toString();
    String _accumulate_2 = accumulate;
    accumulate = (_accumulate_2 + str);
    Reference<? extends NamedElement> current = vr.getTail();
    while ((!Objects.equal(current, null))) {
      {
        String _accumulate_3 = accumulate;
        CharSequence _generateReferencedElement_1 = this.generateReferencedElement(current);
        String _plus = ("->" + _generateReferencedElement_1);
        accumulate = (_accumulate_3 + _plus);
        Reference<? extends NamedElement> _tail = current.getTail();
        current = _tail;
      }
    }
    return accumulate;
  }
  
  /**
   * brief explanation about what does this method
   * @param reference the reference...
   * @return referenced element converted to a formatted string
   */
  public CharSequence generateReferencedElement(final Reference reference) {
    CharSequence _switchResult = null;
    NamedElement _referencedElement = reference.getReferencedElement();
    boolean _matched = false;
    if (!_matched) {
      if (_referencedElement instanceof Attribute) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        NamedElement _referencedElement_1 = reference.getReferencedElement();
        String _name = _referencedElement_1.getName();
        _builder.append(_name, "");
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (_referencedElement instanceof Variable) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        NamedElement _referencedElement_1 = reference.getReferencedElement();
        String _name = _referencedElement_1.getName();
        _builder.append(_name, "");
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (_referencedElement instanceof Parameter) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        NamedElement _referencedElement_1 = reference.getReferencedElement();
        String _name = _referencedElement_1.getName();
        _builder.append(_name, "");
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      if (_referencedElement instanceof Method) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        NamedElement _referencedElement_1 = reference.getReferencedElement();
        String _name = _referencedElement_1.getName();
        _builder.append(_name, "");
        _builder.append("(");
        CharSequence _parameters = this.getParameters(((ParameterizedReference) reference));
        _builder.append(_parameters, "");
        _builder.append(")");
        _switchResult = _builder;
      }
    }
    if (!_matched) {
      _switchResult = reference.toString();
    }
    return _switchResult;
  }
  
  public CharSequence getParameters(final ParameterizedReference<?> reference) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Expression> _parameters = reference.getParameters();
      int _size = _parameters.size();
      boolean _notEquals = (_size != 0);
      if (_notEquals) {
        {
          EList<Expression> _parameters_1 = reference.getParameters();
          boolean _hasElements = false;
          for(final Expression param : _parameters_1) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(",", "");
            }
            CharSequence _valueTemplate = this.valueTemplate(param);
            _builder.append(_valueTemplate, "");
          }
        }
      }
    }
    return _builder;
  }
  
  public boolean hasTail(final Reference r) {
    Reference _tail = r.getTail();
    boolean _notEquals = (!Objects.equal(_tail, null));
    if (_notEquals) {
      return true;
    } else {
      return false;
    }
  }
  
  public CharSequence generateArray(final ViewInstance instance) {
    String accumulate = "";
    String string = "";
    EList<Expression> _parameters = instance.getParameters();
    int _size = _parameters.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      String _accumulate = accumulate;
      Type _type = instance.getType();
      TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
      EList<Parameter> _parameters_1 = _typeSpecification.getParameters();
      Parameter _get = _parameters_1.get((i).intValue());
      String _name = _get.getName();
      String _plus = ("\'" + _name);
      String _plus_1 = (_plus + "\'");
      String _plus_2 = (_plus_1 + "=>");
      EList<Expression> _parameters_2 = instance.getParameters();
      Expression _get_1 = _parameters_2.get((i).intValue());
      CharSequence _valueTemplate = this.valueTemplate(_get_1);
      String _plus_3 = (_plus_2 + _valueTemplate);
      String _plus_4 = (_plus_3 + ", ");
      accumulate = (_accumulate + _plus_4);
    }
    int _length = accumulate.length();
    int _minus = (_length - 2);
    String _substring = accumulate.substring(0, _minus);
    accumulate = _substring;
    string = (("[" + accumulate) + "]");
    return string;
  }
  
  public String namedUrlForController(final Controller c) {
    String guia = "-controller";
    String nombreCompl = c.getName();
    String cadena = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, nombreCompl);
    String _cadena = cadena;
    cadena = (_cadena + guia);
    return cadena;
  }
  
  public String namedUrlForActionCall(final ActionCall ac) {
    Action _action = this._ismlModelNavigation.getAction(ac);
    EObject _eContainer = _action.eContainer();
    Controller _cast = this._ismlModelNavigation.<EObject, Controller>cast(_eContainer, Controller.class);
    String _namedUrlForController = this.namedUrlForController(_cast);
    String _plus = ("/" + _namedUrlForController);
    String _plus_1 = (_plus + "/");
    Action _action_1 = this._ismlModelNavigation.getAction(ac);
    String _name = _action_1.getName();
    String _kebabCase = this.toKebabCase(_name);
    String _plus_2 = (_plus_1 + _kebabCase);
    String _plus_3 = (_plus_2 + "/");
    CharSequence _generateParametersActionCall = this.generateParametersActionCall(ac);
    return (_plus_3 + _generateParametersActionCall);
  }
  
  public CharSequence generateParametersActionCall(final ActionCall call) {
    StringConcatenation _builder = new StringConcatenation();
    String generatedParameters = _builder.toString();
    EList<Expression> _parameters = call.getParameters();
    int _size = _parameters.size();
    boolean _greaterThan = (_size > 0);
    if (_greaterThan) {
      StringConcatenation _builder_1 = new StringConcatenation();
      {
        EList<Expression> _parameters_1 = call.getParameters();
        for(final Expression param : _parameters_1) {
          _builder_1.append("{{");
          CharSequence _valueTemplateForEntities = this.valueTemplateForEntities(param);
          _builder_1.append(_valueTemplateForEntities, "");
          _builder_1.append("}}");
        }
      }
      generatedParameters = _builder_1.toString();
    }
    return generatedParameters;
  }
  
  public CharSequence valueTemplateForEntities(final Expression e) {
    if ((e instanceof VariableReference)) {
      VariableTypeElement _referencedElement = ((VariableReference)e).getReferencedElement();
      Type _type = _referencedElement.getType();
      TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
      if ((_typeSpecification instanceof Entity)) {
        StringConcatenation _builder = new StringConcatenation();
        {
          boolean _hasTail = this.hasTail(((Reference)e));
          if (_hasTail) {
            CharSequence _generateTailedElement = this.generateTailedElement(((VariableReference)e));
            _builder.append(_generateTailedElement, "");
          } else {
            _builder.append("$");
            VariableTypeElement _referencedElement_1 = ((VariableReference)e).getReferencedElement();
            String _name = _referencedElement_1.getName();
            _builder.append(_name, "");
          }
        }
        _builder.append("->id");
        return _builder;
      }
    }
    return this.valueTemplate(e);
  }
}
