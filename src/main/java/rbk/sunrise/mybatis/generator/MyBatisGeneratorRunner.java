package rbk.sunrise.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p>由于maven插件运行时，不能将本项目的包含到其classpath中，所以会引用不到项目中自己写的类。
 * 于是先用JavaRunner。</p>
 */
public class MyBatisGeneratorRunner {
    public static void main(String[] args) throws Exception{
        List<String> warnings = new ArrayList<String>();
        URL url = Thread.currentThread().getContextClassLoader().getResource("generatorConfig.xml");
        File configFile = new File(url.toURI());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(false);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
