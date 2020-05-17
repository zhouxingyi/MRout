package com.fishman.zxy.annotion_compiler;

import com.fishman.zxy.annotion.BindPath;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class AnnotionCompiler extends AbstractProcessor {

    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer=processingEnvironment.getFiler();
    }

    /**
     * 注解处理器支持的java的原版本
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 声明注解处理器要处理的注解
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types=new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    /**
     * 编译时执行的方法
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //获取到当前模块用到BindPath的结点
        // TypeElement 类节点
        //ExecutableElement 方法节点
        //VariableElement  成员变量节点
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        Map<String,String>map=new HashMap<>();
        for (Element element:elementsAnnotatedWith) {
            TypeElement typeElement= (TypeElement) element;
            //获取到activity 上的BindPath注解
            BindPath annotation = typeElement.getAnnotation(BindPath.class);
            String key=annotation.value();
            //获取到包名加类名
            Name activityName = typeElement.getQualifiedName();
            map.put(key,activityName+".class");
        }
        //写文件
        if(map.size()>0){
            Writer writer=null;
            //文件类名
            String activityUtil="ActivityUtil"+System.currentTimeMillis();
            //生成java文件
            try {
                JavaFileObject sourceFile=filer.createSourceFile("com.fishman.zxy.util."+activityUtil);
                writer=sourceFile.openWriter();
                StringBuffer buffer=new StringBuffer();
                buffer.append("package  com.fishman.zxy.util;\n");
                buffer.append("import com.fishman.zxy.marouter.ARouter;\n" +
                        "import com.fishman.zxy.marouter.IRout;\n" +
                        "\n" +
                        "public class "+activityUtil+" implements IRout {\n" +
                        "    @Override\n" +
                        "    public void putActivity() {\n");
                Iterator<String> iterator=map.keySet().iterator();
                while (iterator.hasNext()){
                   String key=iterator.next();
                   String ClassnName=map.get(key);
                    buffer.append("      ARouter.getInstance().AddActivity(\""+key+"\","+ClassnName+");\n");
                }
                buffer.append("\n    }\n}");
                writer.write(buffer.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if(writer!=null){
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
