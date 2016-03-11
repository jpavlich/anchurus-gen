package co.edu.javeriana.anchurus.generator.laravel.generators;

import co.edu.javeriana.anchurus.generator.laravel.AnchurusLaravelGenerator;
import co.edu.javeriana.anchurus.generator.laravel.templates.RoutesTemplate;
import co.edu.javeriana.isml.generator.common.SimpleGenerator;
import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Controller;
import java.util.List;

@SuppressWarnings("all")
public class RoutesGenerator extends SimpleGenerator<List<Controller>> {
  @Override
  protected String getFileName(final List<Controller> e) {
    return "routes.php";
  }
  
  @Override
  protected String getOutputConfigurationName() {
    return AnchurusLaravelGenerator.ROUTES;
  }
  
  @Override
  public SimpleTemplate<List<Controller>> getTemplate() {
    return new RoutesTemplate();
  }
}
