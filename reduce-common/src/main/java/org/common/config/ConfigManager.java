package org.common.config;

import com.netflix.config.ConcurrentCompositeConfiguration;
import com.netflix.config.ConcurrentMapConfiguration;
import com.netflix.config.DynamicPropertyFactory;
import org.apache.commons.configuration.*;

import java.util.Collections;
import java.util.Map;

/**
 * 配置管理
 *
 * @author zy
 */
public class ConfigManager {

    /**
     * 底层配置依赖的configuration
     */
    private final AbstractConfiguration configuration;

    /**
     * 动态配置的property 工厂
     */
    private final DynamicPropertyFactory propertyFactory;

    private static final ConfigManager INSTANCE = new ConfigManager();

    public ConfigManager() {
        this(Collections.emptyMap());
    }

    public ConfigManager(Map<String, Object> config) {
        configuration = createConfiguration(config);
        this.propertyFactory = DynamicPropertyFactory.initWithConfigurationSource(configuration);
    }

    private static ConcurrentCompositeConfiguration createConfiguration(Map<String, Object> configInfo) {
        ConcurrentCompositeConfiguration config = new ConcurrentCompositeConfiguration();

        SystemConfiguration sysConfig = new SystemConfiguration();
        config.addConfiguration(sysConfig, "environment");

        ConcurrentMapConfiguration appOverrideConfig = new ConcurrentMapConfiguration();
        config.addConfiguration(appOverrideConfig, "application");
        //设置index位置，用于写入数据，读取的时候优先从上层读取
        config.setContainerConfigurationIndex(config.getIndexOfConfiguration(appOverrideConfig));
        return config;
    }


    public static Configuration getConfiguration() {
        return getInstance().configuration;
    }

    public static DynamicPropertyFactory getPropertyFactory() {
        return getInstance().propertyFactory;
    }


    public static void setProperty(String key, Object value) {
        getInstance().configuration.setProperty(key, value);
    }

    public static void setProperties(Map<String, Object> properties) {
        AbstractConfiguration config = getInstance().configuration;
        properties.forEach(config::setProperty);
    }

    public static ConfigManager getInstance() {
        return INSTANCE;
    }
}
