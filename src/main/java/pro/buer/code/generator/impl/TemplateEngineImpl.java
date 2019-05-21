package pro.buer.code.generator.impl;

import java.io.StringWriter;
import java.time.LocalDate;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import pro.buer.code.generator.TemplateEngine;
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
public class TemplateEngineImpl implements TemplateEngine{

    VelocityEngine velocityEngine;

    public TemplateEngineImpl(){
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        velocityEngine.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        velocityEngine.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", StructuredGlobbingResourceLoader.class.getName());
        velocityEngine.init();
    }

    @Override
    public String parseTemplate(Profile profile, Bean bean, Module module, Builder builder){
        Template template = velocityEngine.getTemplate(builder.getTemplate());
        VelocityContext context = createContext();
        context.put("profile", profile);
        context.put("bean", bean);
        context.put("module", module);
        context.put("builder", builder);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

    @Override
    public String parseExpression(Profile profile, Bean bean, Module module, String expression){
        VelocityContext context = createContext();
        context.put("profile", profile);
        context.put("bean", bean);
        context.put("module", module);
        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context, writer, "", expression);
        return writer.toString();
    }

    private VelocityContext createContext(){
        VelocityContext context = new VelocityContext();
        context.put("date", LocalDate.now());
        return context;
    }
}
