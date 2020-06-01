package com.junlong0716.erouter.complier;

import com.example.erouter.annotation.Query;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import static com.junlong0716.erouter.complier.Constants.CLAZZ_ROUTER_PATH;

/**
 * @author EdisonLi
 * @version 1.0
 * @since 2020-05-31
 */
@AutoService(Processor.class)
public class QueryCompiler extends BaseCompiler {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment environment) {
        // 获取被Query注解标记的所有的Class
        Set<? extends Element> queryElements = environment.getElementsAnnotatedWith(Query.class);
        // 存放被Query注解的 key 为类中的所有被注解的变量
        Map<Element, List<Element>> queryClazzFields = new HashMap<>();
        // 遍历类
        for (Element fieldElement : queryElements) {
            try {
                // 获取被注解的成员变量的父级Element 也就是该成员变量所在的类
                Element clazzElement = fieldElement.getEnclosingElement();
                if (fieldElement.getModifiers().contains(Modifier.PRIVATE)) {
                    throw new IllegalAccessException("The inject fields CAN NOT BE 'private'!!! please check field ["
                            + fieldElement.getSimpleName() + "] in class [" + clazzElement.getSimpleName() + "]");
                }

                // 获取该类所有的被注解变量
                List<Element> queryAllElements = queryClazzFields.get(clazzElement);

                if (queryAllElements == null) {
                    queryAllElements = new ArrayList<>(2);
                    queryClazzFields.put(clazzElement, queryAllElements);
                }

                // 被注解变量 与 被注解变量所在的类 一一对应
                queryAllElements.add(fieldElement);
            } catch (IllegalAccessException e) {
                mLogger.e(e.getMessage());
            }

            // 遍历生成Clazz文件
            for (Map.Entry<Element, List<Element>> elementListEntry : queryClazzFields.entrySet()) {
                // 生成的类名
                Element classElement = elementListEntry.getKey();
                // 被注解的field
                List<Element> queryFields = elementListEntry.getValue();
                generateClazz(classElement, queryFields);
            }
        }
        return true;
    }

    /**
     * 生成文件
     *
     * @param classElement 类
     * @param queryFields  变量
     */
    private void generateClazz(Element classElement, List<Element> queryFields) {
        String clazzNameStr = classElement.getSimpleName().toString();
        mLogger.i("class name str :" + clazzNameStr);
        ClassName clazzName = ClassName.bestGuess(clazzNameStr);
        mLogger.i("class name :" + clazzName);

        // step 1
        /*
            public void bind(TargetActivity target, Bundle extra){

            }
         */

        ClassName bundleClazzName = ClassName.bestGuess(Constants.CLASS_NAME_BUNDLE);

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constants.FUNCTION_QUERY_BIND)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(clazzName, Constants.FUNCTION_QUERY_PARAMETER_NAME_TARGET)
                .addParameter(bundleClazzName, Constants.FUNCTION_QUERY_PARAMETER_NAME_EXTRA);

        // step 2
        /*
           Bundle bundle = data.getBundle(XXX)
         */
        methodBuilder.addStatement("$T extraBundle = extra.getBundle($T.EXTRAS_BUNDLE)", bundleClazzName, ClassName.bestGuess(CLAZZ_ROUTER_PATH));

        for (Element queryField : queryFields) {
            generateQueryData(methodBuilder, queryField);
        }


        // step3
        // create Class XXXX$$QueryBinding.java
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(clazzNameStr +
                Constants.SIMPLE_NAME_SUFFIX_OF_QUERY_BINDING)
                .addSuperinterface(
                        // Add implements IQueryBinding<XXXX>
                        ParameterizedTypeName.get(ClassName.get(Constants.PACKAGE_NAME_EROUTER, Constants.SIMPLE_NAME_INTERFACE_QUERY_BINDING), clazzName)
                )
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                .addMethod(methodBuilder.build());

        // 4. Generate java class.
        try {
            String packageName = mElementUtils.getPackageOf(classElement).getQualifiedName().toString();
            JavaFile.builder(packageName, classBuilder.build())
                    .addFileComment("ERouter-Compiler auto generate")
                    .indent("    ")
                    .build().writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        if (data.containsKey("title")) {
            target.title = (java.lang.String) data.getString("title", target.title);
        } else if (urlDatum != null && urlDatum.containsKey("title")) {
            target.title = (java.lang.String) urlDatum.getString("title", target.title);
        } else {
            // do nothing......
        }
    */
    private void generateQueryData(MethodSpec.Builder methodBuilder, Element queryField) {
        // target.title
        String paramTarget = Constants.FUNCTION_QUERY_PARAMETER_NAME_TARGET + Constants.DOT + queryField.getSimpleName().toString();
        // extra key
        String key = queryField.getAnnotation(Query.class).key();

        methodBuilder.addComment("------------------------------------ @Query(key = \"" + key + "\")------------------------------------");

        methodBuilder.addCode(
                "if(" + Constants.FUNCTION_QUERY_PARAMETER_NAME_EXTRA + Constants.DOT + "containsKey($S)){\n" +
                        generateGetKeyByType1(key, paramTarget, queryField, mTypeUtils.typeExchange(queryField)) + ";\n" +
                        "}else if(extraBundle != null && extraBundle.containsKey($S)){\n" +
                        generateGetKeyByType2(key, paramTarget, queryField, mTypeUtils.typeExchange(queryField)) + ";\n" +
                        "}else {\n" +
                        " // do nothing...\n" +
                        "}\n"
                , key
                , key
        );
    }

    private String generateGetKeyByType2(String key, String paramTarget, Element queryField, int type) {
        String fetchDataCode = "    "+ Constants.FUNCTION_QUERY_PARAMETER_NAME_TARGET +Constants.DOT + queryField.getSimpleName().toString() + " = "
                + CodeBlock.builder().add("($T) ", ClassName.get(queryField.asType())).build().toString();
        switch (QueryType.values()[type]) {
            case BOOLEAN:
                fetchDataCode += CodeBlock.builder()
                        .add("$T.parseBoolean(extraBundle.getString($S))", Boolean.class, key)
                        .build().toString();
                break;
            case BYTE:
                fetchDataCode += CodeBlock.builder()
                        .add("$T.parseByte(extraBundle.getString($S))", Byte.class, key)
                        .build().toString();
                break;
            case SHORT:
                fetchDataCode += CodeBlock.builder()
                        .add("$T.parseShort(extraBundle.getString($S))", Short.class, key)
                        .build().toString();
                break;
            case INT:
                fetchDataCode += CodeBlock.builder()
                        .add("$T.parseInt(extraBundle.getString($S))", Integer.class, key)
                        .build().toString();
                break;
            case LONG:
                fetchDataCode += CodeBlock.builder()
                        .add("$T.parseInt(extraBundle.getString($S))", Integer.class, key)
                        .build().toString();
                break;
            case FLOAT:
                fetchDataCode += CodeBlock.builder()
                        .add("$T.parseFloat(extraBundle.getString($S))", Float.class, key)
                        .build().toString();
                break;
            case DOUBLE:
                fetchDataCode += CodeBlock.builder()
                        .add("$T.parseDouble(extraBundle.getString($S))", Double.class, key)
                        .build().toString();
                break;
            case STRING:
                fetchDataCode += CodeBlock.builder()
                        .add("extraBundle.getString($S, " + paramTarget + ")", key)
                        .build().toString();
                break;
            default:
                return CodeBlock.builder()
                        .add("    throw new $T($S)",
                                UnsupportedOperationException.class,
                                "cannot support this convert that type is " + ClassName.get(queryField.asType()))
                        .build().toString();
        }
        return fetchDataCode;

    }


    private String generateGetKeyByType1(String key, String paramTarget, Element queryField, int type) {
        String code = "    "+ Constants.FUNCTION_QUERY_PARAMETER_NAME_TARGET + Constants.DOT + queryField.getSimpleName().toString() + " = "
                + CodeBlock.builder().add("($T) ", ClassName.get(queryField.asType())).build().toString();
        switch (QueryType.values()[type]) {
            case BOOLEAN:
                code += "extra.getBoolean($S, " + paramTarget + ")";
                break;
            case BYTE:
                code += "extra.getByte($S, " + paramTarget + ")";
                break;
            case SHORT:
                code += "extra.getShort($S, " + paramTarget + ")";
                break;
            case INT:
                code += "extra.getInt($S, " + paramTarget + ")";
                break;
            case LONG:
                code += "extra.getLong($S, " + paramTarget + ")";
                break;
            case CHAR:
                code += "extra.getChar($S, " + paramTarget + ")";
                break;
            case FLOAT:
                code += "extra.getFloat($S, " + paramTarget + ")";
                break;
            case DOUBLE:
                code += "extra.getDouble($S, " + paramTarget + ")";
                break;
            case STRING:
                code += "extra.getString($S, " + paramTarget + ")";
                break;
            case SERIALIZABLE:
                code += "extra.getSerializable($S)";
                break;
            case PARCELABLE:
                code += "extra.getParcelable($S)";
                break;
            default:
                return CodeBlock.builder()
                        .add("    throw new $T($S)",
                                UnsupportedOperationException.class,
                                "cannot support this convert that type is " + ClassName.get(queryField.asType()))
                        .build().toString();
        }
        return CodeBlock.builder().add(code, key).build().toString();
    }
}
