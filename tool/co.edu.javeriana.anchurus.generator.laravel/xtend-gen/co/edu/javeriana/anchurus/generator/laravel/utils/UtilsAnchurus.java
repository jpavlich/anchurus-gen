package co.edu.javeriana.anchurus.generator.laravel.utils;

import co.edu.javeriana.isml.isml.BoolValue;
import co.edu.javeriana.isml.isml.Expression;
import co.edu.javeriana.isml.isml.FloatValue;
import co.edu.javeriana.isml.isml.IntValue;
import co.edu.javeriana.isml.isml.MethodCall;
import co.edu.javeriana.isml.isml.NamedElement;
import co.edu.javeriana.isml.isml.NullValue;
import co.edu.javeriana.isml.isml.Parameter;
import co.edu.javeriana.isml.isml.Reference;
import co.edu.javeriana.isml.isml.StringValue;
import co.edu.javeriana.isml.isml.Type;
import co.edu.javeriana.isml.isml.TypeSpecification;
import co.edu.javeriana.isml.isml.VariableReference;
import co.edu.javeriana.isml.isml.VariableTypeElement;
import co.edu.javeriana.isml.isml.ViewInstance;
import co.edu.javeriana.isml.scoping.IsmlModelNavigation;
import com.google.common.base.CaseFormat;
import com.google.common.base.Objects;
import com.google.inject.Inject;
import java.util.Calendar;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class UtilsAnchurus {
  @Inject
  @Extension
  private IsmlModelNavigation _ismlModelNavigation;
  
  public String toSnakeCase(final String string) {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string);
  }
  
  public Calendar fecha() {
    return Calendar.getInstance();
  }
  
  public CharSequence valueTemplate(final Expression e) {
    CharSequence _xblockexpression = null;
    {
      if ((e instanceof VariableReference)) {
        Reference<?> _tail = ((VariableReference)e).getTail();
        NamedElement _referencedElement = null;
        if (_tail!=null) {
          _referencedElement=_tail.getReferencedElement();
        }
        InputOutput.<NamedElement>println(_referencedElement);
      }
      InputOutput.println();
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
          _builder.append("\"");
          Object _literal = ((StringValue)e).getLiteral();
          String _string = _literal.toString();
          _builder.append(_string, " ");
          _builder.append("\" ");
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
            boolean _hasQueue = this.hasQueue(((VariableReference)e));
            if (_hasQueue) {
              Reference<?> _tail_1 = ((VariableReference)e).getTail();
              NamedElement _referencedElement_1 = _tail_1.getReferencedElement();
              String _name = _referencedElement_1.getName();
              _builder.append(_name, "");
            } else {
              VariableTypeElement _referencedElement_2 = ((VariableReference)e).getReferencedElement();
              String _name_1 = _referencedElement_2.getName();
              _builder.append(_name_1, "");
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
        _switchResult = e.toString();
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  public CharSequence generateArray(final ViewInstance instance) {
    String cadena1 = "[";
    String cadena2 = "]";
    String cadena3 = "=>";
    String acumula = "";
    String cadena = "";
    EList<Expression> _parameters = instance.getParameters();
    int _size = _parameters.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      String _acumula = acumula;
      Type _type = instance.getType();
      TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
      EList<Parameter> _parameters_1 = _typeSpecification.getParameters();
      Parameter _get = _parameters_1.get((i).intValue());
      String _name = _get.getName();
      String _plus = (_name + cadena3);
      EList<Expression> _parameters_2 = instance.getParameters();
      Expression _get_1 = _parameters_2.get((i).intValue());
      CharSequence _valueTemplate = this.valueTemplate(_get_1);
      String _plus_1 = (_plus + _valueTemplate);
      String _plus_2 = (_plus_1 + ",");
      acumula = (_acumula + _plus_2);
    }
    int _length = acumula.length();
    int _minus = (_length - 1);
    String _substring = acumula.substring(0, _minus);
    acumula = _substring;
    cadena = ((cadena1 + acumula) + cadena2);
    return cadena;
  }
  
  public boolean hasQueue(final VariableReference vr) {
    Reference<?> _tail = vr.getTail();
    boolean _notEquals = (!Objects.equal(_tail, null));
    if (_notEquals) {
      return true;
    } else {
      return false;
    }
  }
}
