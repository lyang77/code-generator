package pro.buer.code.generator;

import pro.buer.code.generator.domain.Bean;
import pro.buer.code.generator.domain.configuration.Builder;
import pro.buer.code.generator.domain.configuration.Module;
import pro.buer.code.generator.domain.configuration.Profile;

/**
 *
 *
 * create date:2017/7/25
 * remark:
 */
public interface TemplateEngine{

    String parseTemplate(Profile profile, Bean bean, Module module, Builder builder);

    String parseExpression(Profile profile, Bean bean, Module module, String expression);
}
