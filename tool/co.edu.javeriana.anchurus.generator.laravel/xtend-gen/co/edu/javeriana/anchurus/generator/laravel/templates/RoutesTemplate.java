package co.edu.javeriana.anchurus.generator.laravel.templates;

import co.edu.javeriana.anchurus.generator.laravel.utils.UtilsAnchurus;
import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Action;
import co.edu.javeriana.isml.isml.Controller;
import co.edu.javeriana.isml.isml.Parameter;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class RoutesTemplate extends SimpleTemplate<List<Controller>> {
  @Inject
  @Extension
  private UtilsAnchurus _utilsAnchurus;
  
  @Override
  public void preprocess(final List<Controller> lc) {
    InputOutput.<String>println("funciona esto?");
  }
  
  @Override
  protected CharSequence template(final List<Controller> e) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?php");
    _builder.newLine();
    _builder.newLine();
    {
      for(final Controller ctrl : e) {
        {
          EList<Action> _body = ctrl.getBody();
          Iterable<Action> _filter = Iterables.<Action>filter(_body, Action.class);
          for(final Action act : _filter) {
            _builder.append("Route::match([\'GET\', \'POST\'], \'");
            String _namedUrlForController = this._utilsAnchurus.namedUrlForController(ctrl);
            _builder.append(_namedUrlForController, "");
            _builder.append("/");
            String _name = act.getName();
            String _kebabCase = this._utilsAnchurus.toKebabCase(_name);
            _builder.append(_kebabCase, "");
            CharSequence _generateParameters = this.generateParameters(act);
            _builder.append(_generateParameters, "");
            _builder.append("\', \'");
            String _name_1 = ctrl.getName();
            _builder.append(_name_1, "");
            _builder.append("@");
            String _name_2 = act.getName();
            _builder.append(_name_2, "");
            _builder.append("\');");
            _builder.newLineIfNotEmpty();
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
