package rbk.sunrise.mybatis.generator;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import rbk.sunrise.controller.BaseController;
import rbk.sunrise.entity.BaseEntity;
import rbk.sunrise.entity.IdOnlyEntity;
import rbk.sunrise.service.BaseService;
import tk.mybatis.mapper.generator.MapperPlugin;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansField;

/**
 * <p>添加lombok支持。</p>
 * <p>由于MyBatisGenerator不会生成Controller和Service，这里使用MyBatis拓展的插件来一并生成。</p>
 */
public class MvcGeneratorPlugin extends MapperPlugin {

    // 是否使用lombok风格
    private boolean isLombok = true;


    private static final String CONTROLLER_TARGET_PROJECT = "controllerTargetProject";
    private static final String CONTROLLER_TARGET_PACKAGE = "controllerTargetPackage";


    private static final String SERVICE_TARGET_PROJECT = "serviceTargetProject";
    private static final String SERVICE_TARGET_PACKAGE = "serviceTargetPackage";


    private String controllerTargetProject;
    private String controllerTargetPackage;


    private String serviceTargetProject;
    private String serviceTargetPackage;

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);

        //lombok风格
        String lombok = this.properties.getProperty("lombok");
        if (StringUtility.stringHasValue(lombok)) {
            isLombok = Boolean.parseBoolean(lombok);
        }


        //controller路径
        controllerTargetProject = this.properties.getProperty(CONTROLLER_TARGET_PROJECT);
        controllerTargetPackage = this.properties.getProperty(CONTROLLER_TARGET_PACKAGE);


        //service路径
        serviceTargetProject = this.properties.getProperty(SERVICE_TARGET_PROJECT);
        serviceTargetPackage = this.properties.getProperty(SERVICE_TARGET_PACKAGE);

    }


    /**
     * 生成额外的Java文件，这里用作生成controller和service
     *
     * @param introspectedTable
     * @return
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
            IntrospectedTable introspectedTable) {
        return Arrays.asList(generateController(introspectedTable), generateService(introspectedTable));
    }

    /**
     * 生成controller
     *
     * @param introspectedTable
     * @return
     */
    private GeneratedJavaFile generateController(IntrospectedTable introspectedTable) {
        TopLevelClass topLevelClass = generateAdditional(introspectedTable, this.controllerTargetPackage,
                "Controller", BaseController.class);
        String domainObjectName = topLevelClass.getType().getShortNameWithoutTypeArguments();
        topLevelClass.addAnnotation("@" + Controller.class.getSimpleName());
        topLevelClass.addAnnotation("@" + RequestMapping.class.getSimpleName()
                + "(\"/" + Introspector.decapitalize(domainObjectName) + "\")");

        topLevelClass.addImportedType(Controller.class.getCanonicalName());
        topLevelClass.addImportedType(RequestMapping.class.getCanonicalName());

        return generateJavaFile(topLevelClass, this.serviceTargetProject);
    }


    /**
     * 生成controller和service的内部实现
     *
     * @param introspectedTable
     * @param targetPackage
     * @param nameSuffix
     * @param baseClass
     * @return
     */
    private TopLevelClass generateAdditional(IntrospectedTable introspectedTable,
                                             String targetPackage,
                                             String nameSuffix, Class baseClass) {
        // 类名
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        // 主键
        Field field = getJavaBeansField(introspectedTable.getPrimaryKeyColumns().get(0), context, introspectedTable);

        StringBuilder sb = new StringBuilder();
        sb.append(baseClass.getSimpleName())
                .append("<")
                .append(introspectedTable.getBaseRecordType())
                .append(", ")
                .append(field.getType())
                .append(">");

        // 父类
        FullyQualifiedJavaType superClass = new FullyQualifiedJavaType(sb.toString());

        String typeName = targetPackage + "." + domainObjectName + nameSuffix;
        TopLevelClass topLevelClass = new TopLevelClass(typeName);
        topLevelClass.setSuperClass(superClass);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

        // 添加import
        topLevelClass.addImportedType(introspectedTable.getBaseRecordType());
        topLevelClass.addImportedType(field.getType());
        return topLevelClass;
    }

    /**
     * 生成service
     *
     * @param introspectedTable
     * @return
     */
    private GeneratedJavaFile generateService(IntrospectedTable introspectedTable) {

        TopLevelClass topLevelClass = generateAdditional(introspectedTable, this.serviceTargetPackage,
                "Service", BaseService.class);
        topLevelClass.addAnnotation("@" + Service.class.getSimpleName());
        topLevelClass.addImportedType(Service.class.getCanonicalName());

        return generateJavaFile(topLevelClass, this.serviceTargetProject);
    }

    /**
     * 转换成GeneratedJavaFile
     *
     * @param topLevelClass
     * @param targetProject
     * @return
     */
    private GeneratedJavaFile generateJavaFile(TopLevelClass topLevelClass, String targetProject) {
        return new GeneratedJavaFile(topLevelClass,
                this.controllerTargetProject,
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
    }

    /**
     * 生成基类
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);

        // 添加继承基类
        boolean isIdOnly = false;
        String idOnly = introspectedTable.getTableConfigurationProperty("idOnly");
        if (StringUtility.stringHasValue(idOnly)) {
            isIdOnly = Boolean.parseBoolean(idOnly);
        }

        // 继承的基类是否只包含ID属性，一般用作关联表
        if (isIdOnly) {
            topLevelClass.addImportedType(IdOnlyEntity.class.getCanonicalName());
            topLevelClass.setSuperClass(IdOnlyEntity.class.getSimpleName());
        } else {
            topLevelClass.addImportedType(BaseEntity.class.getCanonicalName());
            topLevelClass.setSuperClass(BaseEntity.class.getSimpleName());
        }


        // 添加lombok风格实体注解
        if (isLombok) {
            topLevelClass.addImportedType("lombok.*");
            topLevelClass.addAnnotation("@Data");
            topLevelClass.addAnnotation("@Builder");
            topLevelClass.addAnnotation("@NoArgsConstructor");
            topLevelClass.addAnnotation("@AllArgsConstructor");
        }

        return true;
    }

    // 使用lombok的话，getter和setter可以省略
    @Override
    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              Plugin.ModelClassType modelClassType) {
        return isLombok ? false : true;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              Plugin.ModelClassType modelClassType) {
        return isLombok ? false : true;
    }


    //下面所有return false的方法都不生成。这些都是基础的CRUD方法，使用通用Mapper实现

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method,
                                                       Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method,
                                                       TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method,
                                                        Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method,
                                                        TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method,
                                                                 TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,
                                                                 TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method,
                                                                 TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        return false;
    }


    @Override
    public boolean sqlMapCountByExampleElementGenerated(XmlElement element,
                                                        IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element,
                                                         IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element,
                                                            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerCountByExampleMethodGenerated(Method method,
                                                         TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerDeleteByExampleMethodGenerated(Method method,
                                                          TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerSelectByExampleWithBLOBsMethodGenerated(
            Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerSelectByExampleWithoutBLOBsMethodGenerated(
            Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerUpdateByExampleSelectiveMethodGenerated(
            Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerUpdateByExampleWithBLOBsMethodGenerated(
            Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerUpdateByExampleWithoutBLOBsMethodGenerated(
            Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        return false;
    }
}
