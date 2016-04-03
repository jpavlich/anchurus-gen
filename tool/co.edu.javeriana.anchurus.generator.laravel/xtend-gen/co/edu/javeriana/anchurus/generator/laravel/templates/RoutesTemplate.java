package co.edu.javeriana.anchurus.generator.laravel.templates;

import co.edu.javeriana.anchurus.generator.laravel.utils.UtilsAnchurus;
import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Action;
import co.edu.javeriana.isml.isml.Controller;
import co.edu.javeriana.isml.isml.Entity;
import co.edu.javeriana.isml.isml.NamedElement;
import co.edu.javeriana.isml.isml.Parameter;
import co.edu.javeriana.isml.isml.Type;
import co.edu.javeriana.isml.isml.TypeSpecification;
import co.edu.javeriana.isml.isml.TypedElement;
import co.edu.javeriana.isml.scoping.IsmlModelNavigation;
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
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

@SuppressWarnings("all")
public class RoutesTemplate extends SimpleTemplate<List<Controller>> {
  @Inject
  @Extension
  private UtilsAnchurus _utilsAnchurus;
  
  @Inject
  @Extension
  private IsmlModelNavigation _ismlModelNavigation;
  
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  private List<TypedElement> imports = new ArrayList<TypedElement>();
  
  private Set<Entity> entitySubGroup = new LinkedHashSet<Entity>();
  
  @Override
  public void preprocess(final List<Controller> lc) {
    for (final Controller ctrl : lc) {
      {
        TreeIterator<EObject> _eAllContents = ctrl.eAllContents();
        Iterator<TypedElement> _filter = Iterators.<TypedElement>filter(_eAllContents, TypedElement.class);
        List<TypedElement> descendants = IteratorExtensions.<TypedElement>toList(_filter);
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
            }
          }
        }
      }
    }
  }
  
  @Override
  protected CharSequence template(final List<Controller> e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?php");
    _builder.newLine();
    _builder.append("Route::get(\'/\', function () {");
    _builder.newLine();
    _builder.append("\t\t    ");
    _builder.append("return view(\'welcome\');");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("});");
    _builder.newLine();
    {
      for(final Controller ctrl : e) {
        {
          String _name = ctrl.getName();
          boolean _equals = _name.equals("DefaultPageDispatcher");
          boolean _not = (!_equals);
          if (_not) {
            {
              EList<Action> _body = ctrl.getBody();
              Iterable<Action> _filter = Iterables.<Action>filter(_body, Action.class);
              for(final Action act : _filter) {
                _builder.append("Route::match([\'GET\', \'POST\'], \'");
                String _namedUrlForController = this._utilsAnchurus.namedUrlForController(ctrl);
                _builder.append(_namedUrlForController, "");
                _builder.append("/");
                String _name_1 = act.getName();
                String _kebabCase = this._utilsAnchurus.toKebabCase(_name_1);
                _builder.append(_kebabCase, "");
                CharSequence _generateParameters = this.generateParameters(act);
                _builder.append(_generateParameters, "");
                _builder.append("\', \'");
                String _name_2 = ctrl.getName();
                _builder.append(_name_2, "");
                _builder.append("Controller@");
                String _name_3 = act.getName();
                _builder.append(_name_3, "");
                _builder.append("\');");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence generateParameters(final Action action) {
    CharSequence _xblockexpression = null;
    {
      StringConcatenation _builder = new StringConcatenation();
      CharSequence cs = _builder;
      CharSequence _xifexpression = null;
      EList<Parameter> _parameters = action.getParameters();
      int _size = _parameters.size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        StringConcatenation _builder_1 = new StringConcatenation();
        {
          EList<Parameter> _parameters_1 = action.getParameters();
          for(final Parameter param : _parameters_1) {
            _builder_1.append("/{");
            String _name = param.getName();
            _builder_1.append(_name, "");
            _builder_1.append("}");
          }
        }
        _xifexpression = cs = _builder_1;
      } else {
        return cs;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
}
