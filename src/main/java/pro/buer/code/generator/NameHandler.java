package pro.buer.code.generator;

import pro.buer.code.generator.domain.Bean;
import pro.buer.code.generator.domain.Field;

/**
 *
 *
 * create date:2017/7/25
 * remark:
 */
public interface NameHandler{

    void processBeanName(Bean bean);

    void processFieldName(Field field);
}
