package com.wuhan.yq.datasource.mapper;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import tk.mybatis.spring.mapper.ClassPathMapperScanner;

@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@ConditionalOnBean(DataSource.class)
@EnableConfigurationProperties(MybatisProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@AutoConfigureBefore(org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration.class)
public class MybatisAutoConfiguration
{
    private static final Logger logger = LoggerFactory.getLogger(MybatisAutoConfiguration.class);
    
    /**
     * This will just scan the same base package as Spring Boot does. If you want more power, you can explicitly use
     * {@link org.mybatis.spring.annotation.MapperScan} but this will get typed mappers working correctly,
     * out-of-the-box, similar to using Spring Data JPA repositories.
     */
    public static class AutoConfiguredMapperScannerRegistrar
        implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware
    {
        
        private BeanFactory beanFactory;
        
        private ResourceLoader resourceLoader;
        
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry)
        {
            
            logger.debug("Searching for mappers annotated with @Mapper");
            
            ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
            
            try
            {
                if (this.resourceLoader != null)
                {
                    scanner.setResourceLoader(this.resourceLoader);
                }
                
                List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
                if (logger.isDebugEnabled())
                {
                    for (String pkg : packages)
                    {
                        logger.debug("Using auto-configuration base package '{}'", pkg);
                    }
                }
                
                scanner.setAnnotationClass(Mapper.class);
                scanner.registerFilters();
                scanner.doScan(StringUtils.toStringArray(packages));
            }
            catch (IllegalStateException ex)
            {
                logger.debug("Could not determine auto-configuration package, automatic mapper scanning disabled.", ex);
            }
        }
        
        @Override
        public void setBeanFactory(BeanFactory beanFactory)
            throws BeansException
        {
            this.beanFactory = beanFactory;
        }
        
        @Override
        public void setResourceLoader(ResourceLoader resourceLoader)
        {
            this.resourceLoader = resourceLoader;
        }
    }
    
    /**
     * {@link org.mybatis.spring.annotation.MapperScan} ultimately ends up creating instances of
     * {@link MapperFactoryBean}. If {@link org.mybatis.spring.annotation.MapperScan} is used then this
     * auto-configuration is not needed. If it is _not_ used, however, then this will bring in a bean registrar and
     * automatically register components based on the same component-scanning path as Spring Boot itself.
     */
    @Configuration
    @Import({AutoConfiguredMapperScannerRegistrar.class})
    public static class MapperScannerRegistrarNotFoundConfiguration
    {
        
        @PostConstruct
        public void afterPropertiesSet()
        {
            logger.debug("No {} found.", MapperFactoryBean.class.getName());
        }
    }
}
