package com.junlong0716.erouter.complier;

import com.example.erouter.annotation.Query;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @author EdisonLi
 * @version 1.0
 * @since 2020-05-31
 */
public abstract class BaseCompiler extends AbstractProcessor {

    /**
     * 用于将创建的类写入到文件
     */
    protected Filer mFiler;
    protected Logger mLogger;
    protected TypeUtils mTypeUtils;
    protected Types mTypes;
    protected Elements mElementUtils;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mLogger = new Logger(processingEnvironment.getMessager());
        mTypes = processingEnvironment.getTypeUtils();
        mElementUtils = processingEnvironment.getElementUtils();
        mTypeUtils = new TypeUtils(mTypes, mElementUtils);
        mLogger.i(">>>>>>>>>>>>>>>>>>>>> init <<<<<<<<<<<<<<<<<<<<<<<");

    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Query.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
